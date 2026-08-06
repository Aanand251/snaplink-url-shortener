package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.AuditLogResponse;
import com.anand.url_shortner.entity.AuditAction;
import com.anand.url_shortner.entity.AuditLog;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void logAction(
            AuditAction action,
            User performedBy,
            User targetUser,
            String remarks
    ) {

        AuditLog auditLog = AuditLog.builder()
                .action(action)
                .performedById(performedBy.getId())
                .performedByEmail(performedBy.getEmail())
                .targetUserId(targetUser.getId())
                .targetUserEmail(targetUser.getEmail())
                .remarks(remarks)
                .createdAt(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);
    }

    @Transactional(readOnly = true)
    public Page<AuditLogResponse> getAuditLogs(
            int page,
            int size
    ) {

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return auditLogRepository
                .findAllByOrderByCreatedAtDesc(pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<AuditLogResponse> getLogsByAction(
            AuditAction action,
            int page,
            int size
    ) {

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return auditLogRepository
                .findByActionOrderByCreatedAtDesc(
                        action,
                        pageable
                )
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<AuditLogResponse> getLogsByAdminEmail(
            String email,
            int page,
            int size
    ) {

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return auditLogRepository
                .findByPerformedByEmailContainingIgnoreCaseOrderByCreatedAtDesc(
                        email,
                        pageable
                )
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<AuditLogResponse> getLogsByTargetUserEmail(
            String email,
            int page,
            int size
    ) {

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return auditLogRepository
                .findByTargetUserEmailContainingIgnoreCaseOrderByCreatedAtDesc(
                        email,
                        pageable
                )
                .map(this::mapToResponse);
    }

    private AuditLogResponse mapToResponse(
            AuditLog auditLog
    ) {

        return AuditLogResponse.builder()
                .id(auditLog.getId())
                .action(auditLog.getAction())
                .performedBy(auditLog.getPerformedByEmail())
                .targetUser(auditLog.getTargetUserEmail())
                .remarks(auditLog.getRemarks())
                .createdAt(auditLog.getCreatedAt())
                .build();
    }
}