package com.anand.url_shortner.service;

import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.exception.UrlNotFoundException;
import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.util.CacheKeys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedirectServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private RedisService redisService;

    @InjectMocks
    private RedirectService redirectService;

    @Test
    void shouldReturnOriginalUrlFromCache() {

        String shortCode = "abcd";
        String originalUrl = "https://github.com";

        when(redisService.get(CacheKeys.url(shortCode)))
                .thenReturn(originalUrl);

        String result = redirectService.getOriginalUrl(shortCode);

        assertEquals(originalUrl, result);

        verify(redisService).get(CacheKeys.url(shortCode));

        verify(urlRepository, never()).findByShortCode(anyString());
    }

    @Test
    void shouldReturnOriginalUrlFromDatabaseWhenCacheMiss() {

        String shortCode = "abcd";
        String originalUrl = "https://github.com";

        UrlMapping url = new UrlMapping();
        url.setOriginalUrl(originalUrl);

        when(redisService.get(CacheKeys.url(shortCode)))
                .thenReturn(null);

        when(urlRepository.findByShortCode(shortCode))
                .thenReturn(Optional.of(url));

        String result = redirectService.getOriginalUrl(shortCode);

        assertEquals(originalUrl, result);

        verify(urlRepository).findByShortCode(shortCode);

        verify(redisService).save(
                eq(CacheKeys.url(shortCode)),
                eq(originalUrl),
                eq(Duration.ofHours(24))
        );
    }

    @Test
    void shouldThrowExceptionWhenShortCodeNotFound() {

        String shortCode = "abcd";

        when(redisService.get(CacheKeys.url(shortCode)))
                .thenReturn(null);

        when(urlRepository.findByShortCode(shortCode))
                .thenReturn(Optional.empty());

        assertThrows(
                UrlNotFoundException.class,
                () -> redirectService.getOriginalUrl(shortCode)
        );

        verify(urlRepository).findByShortCode(shortCode);
    }
}