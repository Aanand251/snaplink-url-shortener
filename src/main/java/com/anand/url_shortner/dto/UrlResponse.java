package com.anand.url_shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UrlResponse {

    private Long id;

    private String originalUrl;

    private String shortCode;

    private LocalDateTime createdAt;
}