package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.AuditLogResponse;
import com.anand.url_shortner.entity.AuditAction;
import com.anand.url_shortner.entity.AuditLog;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.repository.AuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditLogService auditLogService;

    private User admin;
    private User target;
    private AuditLog auditLog;

    @BeforeEach
    void setUp() {

        admin = User.builder()
                .id(1L)
                .email("admin@gmail.com")
                .build();

        target = User.builder()
                .id(2L)
                .email("user@gmail.com")
                .build();

        auditLog = AuditLog.builder()
                .id(1L)
                .action(AuditAction.USER_MARKED)
                .performedById(1L)
                .performedByEmail("admin@gmail.com")
                .targetUserId(2L)
                .targetUserEmail("user@gmail.com")
                .remarks("SPAM")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Log Action Should Save Audit Log")
    void logAction_shouldSaveAuditLog() {

        auditLogService.logAction(
                AuditAction.USER_MARKED,
                admin,
                target,
                "SPAM"
        );

        ArgumentCaptor<AuditLog> captor =
                ArgumentCaptor.forClass(AuditLog.class);

        verify(auditLogRepository)
                .save(captor.capture());

        AuditLog saved = captor.getValue();

        assertEquals(
                AuditAction.USER_MARKED,
                saved.getAction()
        );

        assertEquals(
                admin.getId(),
                saved.getPerformedById()
        );

        assertEquals(
                target.getId(),
                saved.getTargetUserId()
        );

        assertEquals(
                "SPAM",
                saved.getRemarks()
        );

        assertNotNull(saved.getCreatedAt());
    }

    @Test
    @DisplayName("Get Audit Logs Should Return Page")
    void getAuditLogs_shouldReturnPage() {

        Page<AuditLog> page =
                new PageImpl<>(List.of(auditLog));

        when(auditLogRepository
                .findAllByOrderByCreatedAtDesc(any(Pageable.class)))
                .thenReturn(page);

        Page<AuditLogResponse> response =
                auditLogService.getAuditLogs(0,10);

        assertEquals(1,response.getTotalElements());

        assertEquals(
                AuditAction.USER_MARKED,
                response.getContent()
                        .get(0)
                        .getAction()
        );

        verify(auditLogRepository)
                .findAllByOrderByCreatedAtDesc(any(Pageable.class));
    }

    @Test
    @DisplayName("Get Audit Logs Should Return Empty Page")
    void getAuditLogs_shouldReturnEmptyPage() {

        when(auditLogRepository
                .findAllByOrderByCreatedAtDesc(any(Pageable.class)))
                .thenReturn(Page.empty());

        Page<AuditLogResponse> response =
                auditLogService.getAuditLogs(0,10);

        assertTrue(response.isEmpty());

        verify(auditLogRepository)
                .findAllByOrderByCreatedAtDesc(any(Pageable.class));
    }
    @Test
    @DisplayName("Get Logs By Action Should Return Matching Logs")
    void getLogsByAction_shouldReturnMatchingLogs() {

        Page<AuditLog> page =
                new PageImpl<>(List.of(auditLog));

        when(auditLogRepository
                .findByActionOrderByCreatedAtDesc(
                        eq(AuditAction.USER_MARKED),
                        any(Pageable.class)
                ))
                .thenReturn(page);

        Page<AuditLogResponse> response =
                auditLogService.getLogsByAction(
                        AuditAction.USER_MARKED,
                        0,
                        10
                );

        assertEquals(
                1,
                response.getTotalElements()
        );

        assertEquals(
                AuditAction.USER_MARKED,
                response.getContent()
                        .get(0)
                        .getAction()
        );

        verify(auditLogRepository)
                .findByActionOrderByCreatedAtDesc(
                        eq(AuditAction.USER_MARKED),
                        any(Pageable.class)
                );
    }

    @Test
    @DisplayName("Get Logs By Admin Email Should Return Matching Logs")
    void getLogsByAdminEmail_shouldReturnMatchingLogs() {

        Page<AuditLog> page =
                new PageImpl<>(List.of(auditLog));

        when(auditLogRepository
                .findByPerformedByEmailContainingIgnoreCaseOrderByCreatedAtDesc(
                        eq("admin@gmail.com"),
                        any(Pageable.class)
                ))
                .thenReturn(page);

        Page<AuditLogResponse> response =
                auditLogService.getLogsByAdminEmail(
                        "admin@gmail.com",
                        0,
                        10
                );

        assertEquals(
                1,
                response.getTotalElements()
        );

        assertEquals(
                "admin@gmail.com",
                response.getContent()
                        .get(0)
                        .getPerformedBy()
        );

        verify(auditLogRepository)
                .findByPerformedByEmailContainingIgnoreCaseOrderByCreatedAtDesc(
                        eq("admin@gmail.com"),
                        any(Pageable.class)
                );
    }

    @Test
    @DisplayName("Get Logs By Target User Email Should Return Matching Logs")
    void getLogsByTargetUserEmail_shouldReturnMatchingLogs() {

        Page<AuditLog> page =
                new PageImpl<>(List.of(auditLog));

        when(auditLogRepository
                .findByTargetUserEmailContainingIgnoreCaseOrderByCreatedAtDesc(
                        eq("user@gmail.com"),
                        any(Pageable.class)
                ))
                .thenReturn(page);

        Page<AuditLogResponse> response =
                auditLogService.getLogsByTargetUserEmail(
                        "user@gmail.com",
                        0,
                        10
                );

        assertEquals(
                1,
                response.getTotalElements()
        );

        assertEquals(
                "user@gmail.com",
                response.getContent()
                        .get(0)
                        .getTargetUser()
        );

        verify(auditLogRepository)
                .findByTargetUserEmailContainingIgnoreCaseOrderByCreatedAtDesc(
                        eq("user@gmail.com"),
                        any(Pageable.class)
                );
    }

    @Test
    @DisplayName("Get Logs By Action Should Return Empty Page")
    void getLogsByAction_shouldReturnEmptyPage() {

        when(auditLogRepository
                .findByActionOrderByCreatedAtDesc(
                        eq(AuditAction.USER_DELETED),
                        any(Pageable.class)
                ))
                .thenReturn(Page.empty());

        Page<AuditLogResponse> response =
                auditLogService.getLogsByAction(
                        AuditAction.USER_DELETED,
                        0,
                        10
                );

        assertTrue(response.isEmpty());

        verify(auditLogRepository)
                .findByActionOrderByCreatedAtDesc(
                        eq(AuditAction.USER_DELETED),
                        any(Pageable.class)
                );
    }

    @Test
    @DisplayName("Get Logs By Admin Email Should Return Empty Page")
    void getLogsByAdminEmail_shouldReturnEmptyPage() {

        when(auditLogRepository
                .findByPerformedByEmailContainingIgnoreCaseOrderByCreatedAtDesc(
                        eq("unknown@gmail.com"),
                        any(Pageable.class)
                ))
                .thenReturn(Page.empty());

        Page<AuditLogResponse> response =
                auditLogService.getLogsByAdminEmail(
                        "unknown@gmail.com",
                        0,
                        10
                );

        assertTrue(response.isEmpty());

        verify(auditLogRepository)
                .findByPerformedByEmailContainingIgnoreCaseOrderByCreatedAtDesc(
                        eq("unknown@gmail.com"),
                        any(Pageable.class)
                );
    }

    @Test
    @DisplayName("Get Logs By Target User Email Should Return Empty Page")
    void getLogsByTargetUserEmail_shouldReturnEmptyPage() {

        when(auditLogRepository
                .findByTargetUserEmailContainingIgnoreCaseOrderByCreatedAtDesc(
                        eq("unknown@gmail.com"),
                        any(Pageable.class)
                ))
                .thenReturn(Page.empty());

        Page<AuditLogResponse> response =
                auditLogService.getLogsByTargetUserEmail(
                        "unknown@gmail.com",
                        0,
                        10
                );

        assertTrue(response.isEmpty());

        verify(auditLogRepository)
                .findByTargetUserEmailContainingIgnoreCaseOrderByCreatedAtDesc(
                        eq("unknown@gmail.com"),
                        any(Pageable.class)
                );
    }
}