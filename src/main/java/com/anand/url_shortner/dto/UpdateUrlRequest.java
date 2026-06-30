package com.anand.url_shortner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUrlRequest {

    @NotBlank(message = "Original URL is required")
    private String originalUrl;
}