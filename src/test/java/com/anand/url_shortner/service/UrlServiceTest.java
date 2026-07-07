package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.CreateUrlRequest;
import com.anand.url_shortner.dto.UpdateUrlRequest;
import com.anand.url_shortner.dto.UrlResponse;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.exception.ResourceAccessDeniedException;
import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.util.CacheKeys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UserService userService;

    @Mock
    private RedisService redisService;

    @InjectMocks
    private UrlService urlService;

    @Test
    void shouldCreateShortUrlSuccessfully() {
        CreateUrlRequest request = new CreateUrlRequest();
            request.setOriginalUrl("https://github.com");

            User user = new User();

            when(userService.getCurrentUser()).thenReturn(user);
            when(urlRepository.findByShortCode(any())).thenReturn(Optional.empty());

            String shortCode = urlService.createShortUrl(request);

            assertNotNull(shortCode);
            assertEquals(4, shortCode.length());

            verify(userService).getCurrentUser();
            verify(urlRepository).save(any(UrlMapping.class));
        }

    @Test
    void shouldThrowWhenOriginalUrlIsBlank() {

        CreateUrlRequest request = new CreateUrlRequest();
        request.setOriginalUrl("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> urlService.createShortUrl(request)
        );

        assertEquals(
                "Original URL cannot be empty",
                exception.getMessage()
        );

        verify(urlRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenExpiryIsInPast() {

        CreateUrlRequest request = new CreateUrlRequest();
        request.setOriginalUrl("https://google.com");
        request.setExpiresAt(LocalDateTime.now().minusDays(1));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> urlService.createShortUrl(request)
        );

        assertEquals(
                "Expiry time must be in the future.",
                exception.getMessage()
        );

        verify(urlRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenAliasIsReserved() {

        CreateUrlRequest request = new CreateUrlRequest();

        request.setOriginalUrl("https://google.com");
        request.setCustomAlias("login");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> urlService.createShortUrl(request)
        );

        assertEquals(
                "This alias is reserved.",
                exception.getMessage()
        );

        verify(urlRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenCustomAliasAlreadyExists() {

        CreateUrlRequest request = new CreateUrlRequest();

        request.setOriginalUrl("https://google.com");
        request.setCustomAlias("github");

        UrlMapping url = new UrlMapping();

        when(urlRepository.findByShortCode("github"))
                .thenReturn(Optional.of(url));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> urlService.createShortUrl(request)
        );

        assertEquals(
                "Custom alias already exists.",
                exception.getMessage()
        );

        verify(urlRepository, never()).save(any());
    }

    @Test
    void shouldCreateWithCustomAlias() {

        CreateUrlRequest request = new CreateUrlRequest();
        request.setOriginalUrl("https://github.com");
        request.setCustomAlias("github");

        User user = new User();

        when(userService.getCurrentUser()).thenReturn(user);
        when(urlRepository.findByShortCode("github"))
                .thenReturn(Optional.empty());

        String shortCode = urlService.createShortUrl(request);

        assertEquals("github", shortCode);

        verify(urlRepository).save(any(UrlMapping.class));
    }

    @Test
    void shouldReturnAllUrls() {

        UrlMapping url1 = new UrlMapping();
        UrlMapping url2 = new UrlMapping();

        when(urlRepository.findAll())
                .thenReturn(List.of(url1, url2));

        List<UrlMapping> result = urlService.getAllUrls();

        assertEquals(2, result.size());

        verify(urlRepository).findAll();
    }

    @Test
    void shouldReturnMyUrls() {

        User user = new User();

        UrlMapping url = new UrlMapping();
        url.setId(1L);
        url.setOriginalUrl("https://google.com");
        url.setShortCode("abcd");
        url.setCreatedAt(LocalDateTime.now());

        when(userService.getCurrentUser())
                .thenReturn(user);

        when(urlRepository.findByUser(user))
                .thenReturn(List.of(url));

        List<UrlResponse> result = urlService.getMyUrls();

        assertEquals(1, result.size());
        assertEquals("abcd", result.getFirst().getShortCode());

        verify(urlRepository).findByUser(user);
    }

    @Test
    void shouldDeleteUrl() {

        User user = new User();

        UrlMapping url = new UrlMapping();
        url.setId(1L);
        url.setShortCode("abcd");

        when(userService.getCurrentUser())
                .thenReturn(user);

        when(urlRepository.findByIdAndUser(1L, user))
                .thenReturn(Optional.of(url));

        urlService.deleteUrl(1L);

        verify(urlRepository).delete(url);

        verify(redisService)
                .delete(CacheKeys.url("abcd"));
    }

    @Test
    void shouldThrowWhenDeletingAnotherUsersUrl() {

        User user = new User();

        when(userService.getCurrentUser())
                .thenReturn(user);

        when(urlRepository.findByIdAndUser(1L, user))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceAccessDeniedException.class,
                () -> urlService.deleteUrl(1L)
        );

        verify(urlRepository, never()).delete(any());
    }

    @Test
    void shouldUpdateUrl() {

        User user = new User();

        UrlMapping url = new UrlMapping();
        url.setId(1L);
        url.setShortCode("abcd");

        UpdateUrlRequest request = new UpdateUrlRequest();
        request.setOriginalUrl("https://spring.io");

        when(userService.getCurrentUser())
                .thenReturn(user);

        when(urlRepository.findByIdAndUser(1L, user))
                .thenReturn(Optional.of(url));

        urlService.updateUrl(1L, request);

        assertEquals(
                "https://spring.io",
                url.getOriginalUrl()
        );

        verify(urlRepository).save(url);

        verify(redisService)
                .delete(CacheKeys.url("abcd"));
    }

    @Test
    void shouldThrowWhenUpdatingAnotherUsersUrl() {

        User user = new User();

        UpdateUrlRequest request = new UpdateUrlRequest();
        request.setOriginalUrl("https://spring.io");

        when(userService.getCurrentUser())
                .thenReturn(user);

        when(urlRepository.findByIdAndUser(1L, user))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceAccessDeniedException.class,
                () -> urlService.updateUrl(1L, request)
        );

        verify(urlRepository, never()).save(any());
    }



    }

