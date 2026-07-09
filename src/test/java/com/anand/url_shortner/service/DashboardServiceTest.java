package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.DashboardResponse;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.repository.ClickRepository;
import com.anand.url_shortner.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private ClickRepository clickRepository;

    @Mock
    private ClickTrackingService clickTrackingService;

    @InjectMocks
    private DashboardService dashboardService;

    private UrlMapping urlMapping;

    @BeforeEach
    void setUp() {

        urlMapping = new UrlMapping();

        urlMapping.setOriginalUrl("https://google.com");
        urlMapping.setShortCode("abcd");
        urlMapping.setCreatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Dashboard Should Return Analytics")
    void shouldReturnDashboard() {

        when(urlRepository.findByShortCode("abcd"))
                .thenReturn(Optional.of(urlMapping));

        when(clickTrackingService.getClickCount("abcd"))
                .thenReturn(25L);

        when(clickRepository.findTopBrowser("abcd"))
                .thenReturn("Chrome");

        when(clickRepository.findTopDevice("abcd"))
                .thenReturn("Desktop");

        when(clickRepository.findTopCountry("abcd"))
                .thenReturn("India");

        when(clickRepository.findLastClickedAt("abcd"))
                .thenReturn(LocalDateTime.now());

        DashboardResponse response =
                dashboardService.getDashboard("abcd");

        assertNotNull(response);

        assertEquals(
                "https://google.com",
                response.getOriginalUrl()
        );

        assertEquals(
                "abcd",
                response.getShortCode()
        );

        assertEquals(
                25,
                response.getTotalClicks()
        );

        assertEquals(
                "Chrome",
                response.getTopBrowser()
        );

        assertEquals(
                "Desktop",
                response.getTopDevice()
        );

        assertEquals(
                "India",
                response.getTopCountry()
        );
    }

    @Test
    @DisplayName("Should Throw Exception When URL Not Found")
    void shouldThrowExceptionWhenShortCodeNotFound() {

        when(urlRepository.findByShortCode("abcd"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> dashboardService.getDashboard("abcd")
                );

        assertEquals(
                "No url found for shortCode: abcd",
                exception.getMessage()
        );

        verify(clickTrackingService, never())
                .getClickCount(anyString());
    }
}