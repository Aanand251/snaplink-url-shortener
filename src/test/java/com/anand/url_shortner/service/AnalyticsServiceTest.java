package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.AnalyticsResponse;
import com.anand.url_shortner.repository.ClickRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private ClickTrackingService clickTrackingService;

    @Mock
    private ClickRepository clickRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    private String shortCode;

    @BeforeEach
    void setUp() {

        shortCode = "github";
    }

    @Test
    @DisplayName("Should Return Analytics For Short Code")
    void getAnalytics_shouldReturnAnalytics() {

        LocalDateTime lastClickedAt =
                LocalDateTime.of(
                        2026,
                        7,
                        12,
                        1,
                        45
                );

        when(clickTrackingService.getClickCount(shortCode))
                .thenReturn(25L);

        when(clickRepository.findTopBrowser(shortCode))
                .thenReturn("Chrome");

        when(clickRepository.findTopDevice(shortCode))
                .thenReturn("Desktop");

        when(clickRepository.findTopCountry(shortCode))
                .thenReturn("India");

        when(clickRepository.findLastClickedAt(shortCode))
                .thenReturn(lastClickedAt);

        AnalyticsResponse response =
                analyticsService.getAnalytics(shortCode);

        assertEquals(
                shortCode,
                response.getShortCode()
        );

        assertEquals(
                25L,
                response.getClicks()
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

        assertEquals(
                lastClickedAt,
                response.getLastClickedAt()
        );

        verify(clickTrackingService)
                .getClickCount(shortCode);

        verify(clickRepository)
                .findTopBrowser(shortCode);

        verify(clickRepository)
                .findTopDevice(shortCode);

        verify(clickRepository)
                .findTopCountry(shortCode);

        verify(clickRepository)
                .findLastClickedAt(shortCode);
    }

    @Test
    @DisplayName("Should Return Empty Analytics When No Click Data Exists")
    void getAnalytics_shouldReturnEmptyAnalytics() {

        when(clickTrackingService.getClickCount(shortCode))
                .thenReturn(0L);

        when(clickRepository.findTopBrowser(shortCode))
                .thenReturn(null);

        when(clickRepository.findTopDevice(shortCode))
                .thenReturn(null);

        when(clickRepository.findTopCountry(shortCode))
                .thenReturn(null);

        when(clickRepository.findLastClickedAt(shortCode))
                .thenReturn(null);

        AnalyticsResponse response =
                analyticsService.getAnalytics(shortCode);

        assertEquals(
                shortCode,
                response.getShortCode()
        );

        assertEquals(
                0L,
                response.getClicks()
        );

        assertNull(response.getTopBrowser());

        assertNull(response.getTopDevice());

        assertNull(response.getTopCountry());

        assertNull(response.getLastClickedAt());

        verify(clickTrackingService)
                .getClickCount(shortCode);

        verify(clickRepository)
                .findTopBrowser(shortCode);

        verify(clickRepository)
                .findTopDevice(shortCode);

        verify(clickRepository)
                .findTopCountry(shortCode);

        verify(clickRepository)
                .findLastClickedAt(shortCode);
    }
}