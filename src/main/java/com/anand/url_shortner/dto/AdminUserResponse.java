package com.anand.url_shortner.dto;

import com.anand.url_shortner.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserResponse {

    private long id;

    private String name;

    private String email;

    private Role role;

    private LocalDateTime createdAt;
}