package com.anand.url_shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse {

    // Summary

    private String shortCode;

    private long totalClicks;

    private long todayClicks;

    private String topBrowser;

    private String topDevice;

    private String topCountry;

    private LocalDateTime lastClickedAt;

    // Dashboard

    private List<BrowserStatDto> browserStats;

    private List<DailyClickDto> dailyClicks;

    private List<RecentActivityDto> recentActivities;


}