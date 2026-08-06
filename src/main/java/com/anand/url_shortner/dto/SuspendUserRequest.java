package com.anand.url_shortner.dto;

import com.anand.url_shortner.entity.SuspensionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuspendUserRequest {

    @NotNull(message = "Suspension type is required.")
    private SuspensionType suspensionType;

    @Min(value = 1, message = "Duration must be at least 1 day.")
    private Integer durationInDays;
}