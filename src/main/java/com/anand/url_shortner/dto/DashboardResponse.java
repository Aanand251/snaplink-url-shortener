package com.anand.url_shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Data

@NoArgsConstructor
@AllArgsConstructor

public class DashboardResponse {
    private String originalUrl;

    private String shortCode;

    private long totalClicks;

    private String topBrowser;

    private String topDevice;

    private String topCountry;

    private LocalDateTime  createdAt;

    private LocalDateTime lastClickedAt;

}
