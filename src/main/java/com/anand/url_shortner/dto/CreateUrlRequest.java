package com.anand.url_shortner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUrlRequest {


    @NotBlank(message = "URL cannot be empty")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "Invalid URL format"
    )
    private String originalUrl;
}
