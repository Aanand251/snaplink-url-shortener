package com.anand.url_shortner.dto;

import com.anand.url_shortner.entity.Role;
import com.anand.url_shortner.entity.SuspensionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuspendedUserResponse {

    private Long id;

    private String name;

    private String email;

    private Role role;

    private boolean suspended;

    private SuspensionType suspensionType;

    private LocalDateTime suspendedUntil;

    private String suspendedBy;

    private LocalDateTime suspendedAt;
}