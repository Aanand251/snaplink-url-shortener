package com.anand.url_shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentActivityDto {

    private String browser;

    private String device;

    private String country;

    private LocalDateTime clickedAt;

}