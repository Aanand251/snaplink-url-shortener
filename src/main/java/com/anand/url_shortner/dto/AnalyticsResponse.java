package com.anand.url_shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse {

    private String shortCode;

    private long clicks;

    private String topBrowser;

    private String topDevice;

    private String topCountry;

    private LocalDateTime lastClickedAt;
}