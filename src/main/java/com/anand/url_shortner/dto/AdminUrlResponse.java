package com.anand.url_shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUrlResponse {

    private Long id;

    private String originalUrl;

    private String shortCode;

    private long totalClicks;

    private LocalDateTime createdAt;

    private Long userId;

    private String userEmail;
}