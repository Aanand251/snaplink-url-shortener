package com.anand.url_shortner.dto;

import com.anand.url_shortner.entity.MarkReason;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkUserRequest {

    @NotNull(message = "Mark reason is required.")
    private MarkReason reason;
}