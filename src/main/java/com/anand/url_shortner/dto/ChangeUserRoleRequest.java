package com.anand.url_shortner.dto;

import com.anand.url_shortner.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeUserRoleRequest {

    @NotNull(message = "Role is required")
    private Role role;

}