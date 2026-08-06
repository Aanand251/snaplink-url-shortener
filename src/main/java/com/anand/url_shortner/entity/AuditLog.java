package com.anand.url_shortner.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditAction action;

    @Column(nullable = false)
    private Long performedById;

    @Column(nullable = false)
    private String performedByEmail;

    @Column(nullable = false)
    private Long targetUserId;

    @Column(nullable = false)
    private String targetUserEmail;

    @Column(length = 500)
    private String remarks;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}