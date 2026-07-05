package com.anand.url_shortner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Size(
            min = 4,
            max = 30,
            message = "Alias must be between 4 and 30 characters."
    )
    @Pattern(
            regexp = "^[a-zA-Z0-9_-]*$",
            message = "Alias can contain only letters, numbers, '-' and '_'."
    )
    private String customAlias;

    private LocalDateTime expiresAt;
}