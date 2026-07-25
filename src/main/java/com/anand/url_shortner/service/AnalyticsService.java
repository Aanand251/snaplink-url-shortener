package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.AnalyticsResponse;
import com.anand.url_shortner.dto.BrowserStatDto;
import com.anand.url_shortner.dto.DailyClickDto;
import com.anand.url_shortner.dto.RecentActivityDto;
import com.anand.url_shortner.repository.ClickRepository;
import com.anand.url_shortner.repository.projection.BrowserStatProjection;
import com.anand.url_shortner.repository.projection.DailyClickProjection;
import com.anand.url_shortner.repository.projection.RecentActivityProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ClickTrackingService clickTrackingService;
    private final ClickRepository clickRepository;

    public AnalyticsResponse getAnalytics(String shortCode) {

        long totalClicks =
                clickTrackingService.getClickCount(shortCode);

        long todayClicks =
                clickRepository.getTodayClicks(shortCode);

        String topBrowser =
                defaultValue(clickRepository.findTopBrowser(shortCode));

        String topDevice =
                defaultValue(clickRepository.findTopDevice(shortCode));

        String topCountry =
                defaultValue(clickRepository.findTopCountry(shortCode));

        LocalDateTime lastClickedAt =
                clickRepository.findLastClickedAt(shortCode);

        List<BrowserStatDto> browserStats =
                emptyIfNull(clickRepository.getBrowserStats(shortCode))
                        .stream()
                        .map(this::toBrowserStatDto)
                        .toList();

        List<DailyClickDto> dailyClicks =
                emptyIfNull(clickRepository.getDailyClicks(shortCode))
                        .stream()
                        .map(this::toDailyClickDto)
                        .toList();

        List<RecentActivityDto> recentActivities =
                emptyIfNull(clickRepository.getRecentActivities(shortCode))
                        .stream()
                        .map(this::toRecentActivityDto)
                        .toList();

        return new AnalyticsResponse(
                shortCode,
                totalClicks,
                todayClicks,
                topBrowser,
                topDevice,
                topCountry,
                lastClickedAt,
                browserStats,
                dailyClicks,
                recentActivities
        );
    }

    private BrowserStatDto toBrowserStatDto(
            BrowserStatProjection projection
    ) {
        return new BrowserStatDto(
                projection.getBrowser(),
                projection.getClicks()
        );
    }

    private DailyClickDto toDailyClickDto(
            DailyClickProjection projection
    ) {
        return new DailyClickDto(
                projection.getDate(),
                projection.getClicks()
        );
    }

    private RecentActivityDto toRecentActivityDto(
            RecentActivityProjection projection
    ) {
        return new RecentActivityDto(
                projection.getBrowser(),
                projection.getDevice(),
                projection.getCountry(),
                projection.getClickedAt()
        );
    }

    private <T> List<T> emptyIfNull(List<T> list) {
        return list == null ? List.of() : list;
    }

    private String defaultValue(String value) {
        return (value == null || value.isBlank())
                ? "N/A"
                : value;
    }
}