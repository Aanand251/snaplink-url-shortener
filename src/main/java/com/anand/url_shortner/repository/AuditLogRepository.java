package com.anand.url_shortner.repository;

import com.anand.url_shortner.entity.AuditAction;
import com.anand.url_shortner.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {

    Page<AuditLog> findAllByOrderByCreatedAtDesc(
            Pageable pageable
    );

    Page<AuditLog> findByActionOrderByCreatedAtDesc(
            AuditAction action,
            Pageable pageable
    );

    Page<AuditLog> findByPerformedByEmailContainingIgnoreCaseOrderByCreatedAtDesc(
            String email,
            Pageable pageable
    );

    Page<AuditLog> findByTargetUserEmailContainingIgnoreCaseOrderByCreatedAtDesc(
            String email,
            Pageable pageable
    );
}