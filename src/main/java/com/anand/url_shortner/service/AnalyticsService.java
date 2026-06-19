package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.AnalyticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ClickService clickService;

    public AnalyticsResponse getAnalytics(String shortCode) {
        long clicks = clickService.getClickCount(shortCode);

        return new AnalyticsResponse(shortCode, clicks);

    }
}
