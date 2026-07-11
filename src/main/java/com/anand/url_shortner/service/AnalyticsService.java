package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.AnalyticsResponse;
import com.anand.url_shortner.repository.ClickRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ClickTrackingService clickTrackingService;
    private final ClickRepository clickRepository;

    public AnalyticsResponse getAnalytics(String shortCode) {

        long clicks =
                clickTrackingService.getClickCount(shortCode);

        String topBrowser =
                clickRepository.findTopBrowser(shortCode);

        String topDevice =
                clickRepository.findTopDevice(shortCode);

        String topCountry =
                clickRepository.findTopCountry(shortCode);

        LocalDateTime lastClickedAt =
                clickRepository.findLastClickedAt(shortCode);

        return new AnalyticsResponse(
                shortCode,
                clicks,
                topBrowser,
                topDevice,
                topCountry,
                lastClickedAt
        );
    }
}