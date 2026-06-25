package com.anand.url_shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse {
    private String shortCode;
    private long clicks;
    private String topBrowser;
    private String topDevice;
}
