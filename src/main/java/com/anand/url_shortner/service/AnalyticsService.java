package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.AnalyticsResponse;
import com.anand.url_shortner.repository.ClickRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ClickService clickService;
    private final ClickRepository clickRepository;

    public AnalyticsResponse getAnalytics(String shortCode) {
        long clicks = clickService.getClickCount(shortCode);

        String topBrowser = clickRepository.findTopBrowser(shortCode);
        String topDevice = clickRepository.findTopDevice(shortCode);


        return new AnalyticsResponse(shortCode, clicks , topBrowser , topDevice);

    }
}
