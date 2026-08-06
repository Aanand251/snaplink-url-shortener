package com.anand.url_shortner.dto;

import com.anand.url_shortner.entity.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {

    private Long id;

    private AuditAction action;

    private String performedBy;

    private String targetUser;

    private String remarks;

    private LocalDateTime createdAt;
}