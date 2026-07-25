package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.AnalyticsResponse;
import com.anand.url_shortner.repository.ClickRepository;
import com.anand.url_shortner.repository.projection.BrowserStatProjection;
import com.anand.url_shortner.repository.projection.DailyClickProjection;
import com.anand.url_shortner.repository.projection.RecentActivityProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
                LocalDateTime.of(2026, 7, 12, 1, 45);

        BrowserStatProjection browserProjection =
                mock(BrowserStatProjection.class);

        when(browserProjection.getBrowser())
                .thenReturn("Chrome");

        when(browserProjection.getClicks())
                .thenReturn(20L);

        DailyClickProjection dailyProjection =
                mock(DailyClickProjection.class);

        when(dailyProjection.getDate())
                .thenReturn(LocalDate.of(2026, 7, 12));

        when(dailyProjection.getClicks())
                .thenReturn(10L);

        RecentActivityProjection recentProjection =
                mock(RecentActivityProjection.class);

        when(recentProjection.getBrowser())
                .thenReturn("Chrome");

        when(recentProjection.getDevice())
                .thenReturn("Desktop");

        when(recentProjection.getCountry())
                .thenReturn("India");

        when(recentProjection.getClickedAt())
                .thenReturn(lastClickedAt);

        when(clickTrackingService.getClickCount(shortCode))
                .thenReturn(25L);

        when(clickRepository.getTodayClicks(shortCode))
                .thenReturn(5L);

        when(clickRepository.findTopBrowser(shortCode))
                .thenReturn("Chrome");

        when(clickRepository.findTopDevice(shortCode))
                .thenReturn("Desktop");

        when(clickRepository.findTopCountry(shortCode))
                .thenReturn("India");

        when(clickRepository.findLastClickedAt(shortCode))
                .thenReturn(lastClickedAt);

        when(clickRepository.getBrowserStats(shortCode))
                .thenReturn(List.of(browserProjection));

        when(clickRepository.getDailyClicks(shortCode))
                .thenReturn(List.of(dailyProjection));

        when(clickRepository.getRecentActivities(shortCode))
                .thenReturn(List.of(recentProjection));

        AnalyticsResponse response =
                analyticsService.getAnalytics(shortCode);

        assertEquals(shortCode, response.getShortCode());
        assertEquals(25L, response.getTotalClicks());
        assertEquals(5L, response.getTodayClicks());

        assertEquals("Chrome", response.getTopBrowser());
        assertEquals("Desktop", response.getTopDevice());
        assertEquals("India", response.getTopCountry());
        assertEquals(lastClickedAt, response.getLastClickedAt());

        assertEquals(1, response.getBrowserStats().size());
        assertEquals("Chrome",
                response.getBrowserStats().getFirst().getBrowser());
        assertEquals(20L,
                response.getBrowserStats().getFirst().getClicks());

        assertEquals(1, response.getDailyClicks().size());
        assertEquals(LocalDate.of(2026, 7, 12),
                response.getDailyClicks().getFirst().getDate());
        assertEquals(10L,
                response.getDailyClicks().getFirst().getClicks());

        assertEquals(1, response.getRecentActivities().size());
        assertEquals("Chrome",
                response.getRecentActivities().getFirst().getBrowser());
        assertEquals("Desktop",
                response.getRecentActivities().getFirst().getDevice());
        assertEquals("India",
                response.getRecentActivities().getFirst().getCountry());
        assertEquals(lastClickedAt,
                response.getRecentActivities().getFirst().getClickedAt());

        verify(clickTrackingService).getClickCount(shortCode);
        verify(clickRepository).getTodayClicks(shortCode);
        verify(clickRepository).findTopBrowser(shortCode);
        verify(clickRepository).findTopDevice(shortCode);
        verify(clickRepository).findTopCountry(shortCode);
        verify(clickRepository).findLastClickedAt(shortCode);
        verify(clickRepository).getBrowserStats(shortCode);
        verify(clickRepository).getDailyClicks(shortCode);
        verify(clickRepository).getRecentActivities(shortCode);

        verifyNoMoreInteractions(
                clickTrackingService,
                clickRepository
        );
    }

    @Test
    @DisplayName("Should Return Empty Analytics When No Click Data Exists")
    void getAnalytics_shouldReturnEmptyAnalytics() {

        when(clickTrackingService.getClickCount(shortCode))
                .thenReturn(0L);

        when(clickRepository.getTodayClicks(shortCode))
                .thenReturn(0L);

        when(clickRepository.findTopBrowser(shortCode))
                .thenReturn(null);

        when(clickRepository.findTopDevice(shortCode))
                .thenReturn(null);

        when(clickRepository.findTopCountry(shortCode))
                .thenReturn(null);

        when(clickRepository.findLastClickedAt(shortCode))
                .thenReturn(null);

        when(clickRepository.getBrowserStats(shortCode))
                .thenReturn(List.of());

        when(clickRepository.getDailyClicks(shortCode))
                .thenReturn(List.of());

        when(clickRepository.getRecentActivities(shortCode))
                .thenReturn(List.of());

        AnalyticsResponse response =
                analyticsService.getAnalytics(shortCode);

        assertEquals(shortCode, response.getShortCode());
        assertEquals(0L, response.getTotalClicks());
        assertEquals(0L, response.getTodayClicks());

        // Updated because AnalyticsService converts null -> "N/A"
        assertEquals("N/A", response.getTopBrowser());
        assertEquals("N/A", response.getTopDevice());
        assertEquals("N/A", response.getTopCountry());

        assertNull(response.getLastClickedAt());

        assertTrue(response.getBrowserStats().isEmpty());
        assertTrue(response.getDailyClicks().isEmpty());
        assertTrue(response.getRecentActivities().isEmpty());

        verify(clickTrackingService).getClickCount(shortCode);
        verify(clickRepository).getTodayClicks(shortCode);
        verify(clickRepository).findTopBrowser(shortCode);
        verify(clickRepository).findTopDevice(shortCode);
        verify(clickRepository).findTopCountry(shortCode);
        verify(clickRepository).findLastClickedAt(shortCode);
        verify(clickRepository).getBrowserStats(shortCode);
        verify(clickRepository).getDailyClicks(shortCode);
        verify(clickRepository).getRecentActivities(shortCode);

        verifyNoMoreInteractions(
                clickTrackingService,
                clickRepository
        );
    }
}