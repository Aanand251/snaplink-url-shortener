package com.anand.url_shortner.controller;

import com.anand.url_shortner.dto.AnalyticsResponse;
import com.anand.url_shortner.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/analytics"
) public class AnalyticsController {
    private final AnalyticsService analyticsService;
    @GetMapping("/{shortCode}")
    public AnalyticsResponse getAnalytics(@PathVariable String shortCode) {
        return analyticsService.getAnalytics(shortCode);
    }
}
