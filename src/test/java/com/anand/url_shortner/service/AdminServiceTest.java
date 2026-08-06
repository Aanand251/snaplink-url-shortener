package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.*;
import com.anand.url_shortner.entity.*;
import com.anand.url_shortner.exception.BadRequestException;
import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private AdminService adminService;

    private User admin;

    private User user;

    private UrlMapping urlMapping;

    @BeforeEach
    void setUp() {

        admin = User.builder()
                .id(100L)
                .name("Admin")
                .email("admin@gmail.com")
                .role(Role.ADMIN)
                .createdAt(LocalDateTime.now())
                .build();

        admin.setUrls(new ArrayList<>());

        user = User.builder()
                .id(1L)
                .name("Rahul")
                .email("rahul@gmail.com")
                .password("encoded-password")
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        user.setUrls(new ArrayList<>());

        urlMapping = new UrlMapping();

        urlMapping.setId(1L);
        urlMapping.setOriginalUrl("https://google.com");
        urlMapping.setShortCode("abcd");
        urlMapping.setTotalClicks(10L);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlMapping.setUser(user);

        user.getUrls().add(urlMapping);
    }

    @Test
    @DisplayName("Get All Users Should Return User DTOs")
    void getAllUsers_shouldReturnUserDtos() {

        when(userRepository.findAll())
                .thenReturn(List.of(user));

        List<AdminUserResponse> response =
                adminService.getAllUsers();

        assertEquals(1, response.size());

        AdminUserResponse dto = response.get(0);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(Role.USER, dto.getRole());
        assertEquals(1L, dto.getTotalLinks());
        assertEquals(10L, dto.getTotalClicks());
        assertFalse(dto.isMarked());
        assertFalse(dto.isSuspended());

        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Get All Users Should Return Empty List")
    void getAllUsers_shouldReturnEmptyList() {

        when(userRepository.findAll())
                .thenReturn(List.of());

        List<AdminUserResponse> response =
                adminService.getAllUsers();

        assertTrue(response.isEmpty());

        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Get All URLs Should Return URL DTOs")
    void getAllUrls_shouldReturnUrlDtos() {

        when(urlRepository.findAll())
                .thenReturn(List.of(urlMapping));

        List<AdminUrlResponse> response =
                adminService.getAllUrls();

        assertEquals(1, response.size());

        AdminUrlResponse dto = response.get(0);

        assertEquals(
                "https://google.com",
                dto.getOriginalUrl()
        );

        assertEquals(
                "abcd",
                dto.getShortCode()
        );

        assertEquals(
                10L,
                dto.getTotalClicks()
        );

        assertEquals(
                user.getId(),
                dto.getUserId()
        );

        assertEquals(
                user.getEmail(),
                dto.getUserEmail()
        );

        verify(urlRepository).findAll();
    }

    @Test
    @DisplayName("Get All URLs Should Handle Null Owner")
    void getAllUrls_shouldHandleNullOwner() {

        urlMapping.setUser(null);

        when(urlRepository.findAll())
                .thenReturn(List.of(urlMapping));

        List<AdminUrlResponse> response =
                adminService.getAllUrls();

        assertEquals(1, response.size());

        assertNull(response.get(0).getUserId());

        assertNull(response.get(0).getUserEmail());

        verify(urlRepository).findAll();
    }
    @Test
    @DisplayName("Delete User Should Delete Existing User")
    void deleteUser_shouldDeleteExistingUser() {

        when(userService.getCurrentUser())
                .thenReturn(admin);

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        adminService.deleteUser(1L);

        verify(userService).getCurrentUser();

        verify(userRepository).findById(1L);

        verify(auditLogService).logAction(
                eq(AuditAction.USER_DELETED),
                eq(admin),
                eq(user),
                contains(user.getEmail())
        );

        verify(userRepository).delete(user);
    }

    @Test
    @DisplayName("Delete User Should Throw Exception When User Not Found")
    void deleteUser_shouldThrowExceptionWhenUserNotFound() {

        when(userService.getCurrentUser())
                .thenReturn(admin);

        when(userRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> adminService.deleteUser(99L)
        );

        assertEquals(
                "User not found",
                exception.getMessage()
        );

        verify(userRepository, never())
                .delete(any());
    }

    @Test
    @DisplayName("Delete User Should Not Allow Self Deletion")
    void deleteUser_shouldNotAllowSelfDeletion() {

        when(userService.getCurrentUser())
                .thenReturn(admin);

        when(userRepository.findById(100L))
                .thenReturn(java.util.Optional.of(admin));

        assertThrows(
                BadRequestException.class,
                () -> adminService.deleteUser(100L)
        );

        verify(userRepository, never())
                .delete(any());

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Delete User Should Not Allow Admin Deletion")
    void deleteUser_shouldNotAllowAdminDeletion() {

        User anotherAdmin = User.builder()
                .id(200L)
                .email("another@gmail.com")
                .role(Role.ADMIN)
                .build();

        when(userService.getCurrentUser())
                .thenReturn(admin);

        when(userRepository.findById(200L))
                .thenReturn(java.util.Optional.of(anotherAdmin));

        assertThrows(
                BadRequestException.class,
                () -> adminService.deleteUser(200L)
        );

        verify(userRepository, never())
                .delete(any());

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Delete User Should Call Audit Log")
    void deleteUser_shouldCallAuditLog() {

        when(userService.getCurrentUser())
                .thenReturn(admin);

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        adminService.deleteUser(1L);

        verify(auditLogService, times(1))
                .logAction(
                        eq(AuditAction.USER_DELETED),
                        eq(admin),
                        eq(user),
                        contains("Deleted user")
                );
    }
    @Test
    @DisplayName("Change User Role Should Update User Role")
    void changeUserRole_shouldUpdateUserRole() {

        ChangeUserRoleRequest request =
                new ChangeUserRoleRequest();

        request.setRole(Role.ACTING_ADMIN);

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        adminService.changeUserRole(
                1L,
                request
        );

        assertEquals(
                Role.ACTING_ADMIN,
                user.getRole()
        );

        verify(auditLogService).logAction(
                eq(AuditAction.USER_ROLE_CHANGED),
                eq(admin),
                eq(user),
                contains("ACTING_ADMIN")
        );
    }

    @Test
    @DisplayName("Change User Role Should Throw Exception When User Not Found")
    void changeUserRole_shouldThrowExceptionWhenUserNotFound() {

        ChangeUserRoleRequest request =
                new ChangeUserRoleRequest();

        request.setRole(Role.ADMIN);

        when(userRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> adminService.changeUserRole(
                                99L,
                                request
                        )
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Change User Role Should Not Allow Self Role Change")
    void changeUserRole_shouldNotAllowSelfRoleChange() {

        admin.setRole(Role.ADMIN);

        ChangeUserRoleRequest request =
                new ChangeUserRoleRequest();

        request.setRole(Role.USER);

        when(userRepository.findById(100L))
                .thenReturn(java.util.Optional.of(admin));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> adminService.changeUserRole(
                                100L,
                                request
                        )
                );

        assertEquals(
                "You cannot change your own role.",
                exception.getMessage()
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Change User Role Should Call Audit Log")
    void changeUserRole_shouldCallAuditLog() {

        ChangeUserRoleRequest request =
                new ChangeUserRoleRequest();

        request.setRole(Role.ACTING_ADMIN);

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        adminService.changeUserRole(
                1L,
                request
        );

        verify(auditLogService, times(1))
                .logAction(
                        eq(AuditAction.USER_ROLE_CHANGED),
                        eq(admin),
                        eq(user),
                        contains("ACTING_ADMIN")
                );
    }
    @Test
    @DisplayName("Mark User Should Mark User Successfully")
    void markUser_shouldMarkUserSuccessfully() {

        MarkUserRequest request = new MarkUserRequest();
        request.setReason(MarkReason.SPAM);

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        adminService.markUser(1L, request);

        assertTrue(user.isMarked());
        assertEquals(
                MarkReason.SPAM,
                user.getMarkedReason()
        );
        assertEquals(
                admin,
                user.getMarkedBy()
        );
        assertNotNull(
                user.getMarkedAt()
        );

        verify(auditLogService).logAction(
                eq(AuditAction.USER_MARKED),
                eq(admin),
                eq(user),
                eq("SPAM")
        );
    }

    @Test
    @DisplayName("Mark User Should Throw Exception When User Not Found")
    void markUser_shouldThrowExceptionWhenUserNotFound() {

        MarkUserRequest request = new MarkUserRequest();
        request.setReason(MarkReason.SPAM);

        when(userRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> adminService.markUser(
                        99L,
                        request
                )
        );

        assertEquals(
                "User not found",
                exception.getMessage()
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Mark User Should Not Allow Self Mark")
    void markUser_shouldNotAllowSelfMark() {

        MarkUserRequest request = new MarkUserRequest();
        request.setReason(MarkReason.SPAM);

        when(userRepository.findById(100L))
                .thenReturn(java.util.Optional.of(admin));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> adminService.markUser(
                        100L,
                        request
                )
        );

        assertEquals(
                "You cannot mark yourself.",
                exception.getMessage()
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Mark User Should Throw Exception If Already Marked")
    void markUser_shouldThrowExceptionIfAlreadyMarked() {

        user.setMarked(true);

        MarkUserRequest request = new MarkUserRequest();
        request.setReason(MarkReason.SPAM);

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> adminService.markUser(
                        1L,
                        request
                )
        );

        assertEquals(
                "User is already marked.",
                exception.getMessage()
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }
    @Test
    @DisplayName("Mark User Should Call Audit Log")
    void markUser_shouldCallAuditLog() {

        MarkUserRequest request = new MarkUserRequest();
        request.setReason(MarkReason.SPAM);

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        adminService.markUser(1L, request);

        verify(auditLogService, times(1))
                .logAction(
                        eq(AuditAction.USER_MARKED),
                        eq(admin),
                        eq(user),
                        eq("SPAM")
                );
    }

    @Test
    @DisplayName("Unmark User Should Unmark Successfully")
    void unmarkUser_shouldUnmarkSuccessfully() {

        user.setMarked(true);
        user.setMarkedReason(MarkReason.SPAM);
        user.setMarkedBy(admin);
        user.setMarkedAt(LocalDateTime.now());

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        adminService.unmarkUser(1L);

        assertFalse(user.isMarked());
        assertNull(user.getMarkedReason());
        assertNull(user.getMarkedBy());
        assertNull(user.getMarkedAt());

        verify(auditLogService).logAction(
                eq(AuditAction.USER_UNMARKED),
                eq(admin),
                eq(user),
                eq("User unmarked")
        );
    }

    @Test
    @DisplayName("Unmark User Should Throw Exception If User Is Not Marked")
    void unmarkUser_shouldThrowExceptionIfUserIsNotMarked() {

        user.setMarked(false);

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> adminService.unmarkUser(1L)
        );

        assertEquals(
                "User is not marked.",
                exception.getMessage()
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Unmark User Should Throw Exception When User Not Found")
    void unmarkUser_shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        when(userService.getCurrentUser())
                .thenReturn(admin);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> adminService.unmarkUser(99L)
        );

        assertEquals(
                "User not found",
                exception.getMessage()
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Unmark User Should Call Audit Log")
    void unmarkUser_shouldCallAuditLog() {

        user.setMarked(true);
        user.setMarkedReason(MarkReason.SPAM);

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        adminService.unmarkUser(1L);

        verify(auditLogService, times(1))
                .logAction(
                        eq(AuditAction.USER_UNMARKED),
                        eq(admin),
                        eq(user),
                        eq("User unmarked")
                );
    }

    @Test
    @DisplayName("Get Marked Users Should Return Marked Users")
    void getMarkedUsers_shouldReturnMarkedUsers() {

        user.setMarked(true);
        user.setMarkedReason(MarkReason.SPAM);
        user.setMarkedBy(admin);
        user.setMarkedAt(LocalDateTime.now());

        when(userRepository.findByMarkedTrue())
                .thenReturn(List.of(user));

        List<MarkedUserResponse> response =
                adminService.getMarkedUsers();

        assertEquals(1, response.size());
        assertEquals(user.getId(), response.get(0).getId());
        assertEquals(MarkReason.SPAM, response.get(0).getReason());
        assertEquals(admin.getEmail(), response.get(0).getMarkedBy());

        verify(userRepository).findByMarkedTrue();
    }

    @Test
    @DisplayName("Get Marked Users Should Return Empty List")
    void getMarkedUsers_shouldReturnEmptyList() {

        when(userRepository.findByMarkedTrue())
                .thenReturn(List.of());

        List<MarkedUserResponse> response =
                adminService.getMarkedUsers();

        assertTrue(response.isEmpty());

        verify(userRepository).findByMarkedTrue();
    }
    @Test
    @DisplayName("Suspend User Should Soft Suspend Successfully")
    void suspendUser_shouldSoftSuspendSuccessfully() {

        SuspendUserRequest request = new SuspendUserRequest();
        request.setSuspensionType(SuspensionType.SOFT);
        request.setDurationInDays(5);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        adminService.suspendUser(1L, request);

        assertTrue(user.isSuspended());
        assertEquals(
                SuspensionType.SOFT,
                user.getSuspensionType()
        );
        assertEquals(
                admin,
                user.getSuspendedBy()
        );
        assertNotNull(user.getSuspendedAt());
        assertNotNull(user.getSuspendedUntil());

        verify(auditLogService).logAction(
                eq(AuditAction.USER_SUSPENDED),
                eq(admin),
                eq(user),
                contains("SOFT")
        );
    }

    @Test
    @DisplayName("Suspend User Should Hard Suspend Successfully")
    void suspendUser_shouldHardSuspendSuccessfully() {

        SuspendUserRequest request = new SuspendUserRequest();
        request.setSuspensionType(SuspensionType.HARD);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        adminService.suspendUser(1L, request);

        assertTrue(user.isSuspended());
        assertEquals(
                SuspensionType.HARD,
                user.getSuspensionType()
        );

        assertNull(user.getSuspendedUntil());

        verify(auditLogService).logAction(
                eq(AuditAction.USER_SUSPENDED),
                eq(admin),
                eq(user),
                eq("HARD")
        );
    }

    @Test
    @DisplayName("Suspend User Should Throw Exception When User Not Found")
    void suspendUser_shouldThrowExceptionWhenUserNotFound() {

        SuspendUserRequest request = new SuspendUserRequest();
        request.setSuspensionType(SuspensionType.HARD);

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        when(userService.getCurrentUser())
                .thenReturn(admin);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> adminService.suspendUser(
                        99L,
                        request
                )
        );

        assertEquals(
                "User not found",
                exception.getMessage()
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Suspend User Should Not Allow Self Suspension")
    void suspendUser_shouldNotAllowSelfSuspension() {

        SuspendUserRequest request = new SuspendUserRequest();
        request.setSuspensionType(SuspensionType.HARD);

        when(userRepository.findById(100L))
                .thenReturn(Optional.of(admin));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        assertThrows(
                BadRequestException.class,
                () -> adminService.suspendUser(
                        100L,
                        request
                )
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Suspend User Should Not Allow Admin Suspension")
    void suspendUser_shouldNotAllowAdminSuspension() {

        User anotherAdmin = User.builder()
                .id(200L)
                .email("another@gmail.com")
                .role(Role.ADMIN)
                .build();

        SuspendUserRequest request = new SuspendUserRequest();
        request.setSuspensionType(SuspensionType.HARD);

        when(userRepository.findById(200L))
                .thenReturn(Optional.of(anotherAdmin));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        assertThrows(
                BadRequestException.class,
                () -> adminService.suspendUser(
                        200L,
                        request
                )
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }
    @Test
    @DisplayName("Suspend User Should Throw Exception If Already Suspended")
    void suspendUser_shouldThrowExceptionIfAlreadySuspended() {

        user.setSuspended(true);

        SuspendUserRequest request = new SuspendUserRequest();
        request.setSuspensionType(SuspensionType.HARD);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        assertThrows(
                BadRequestException.class,
                () -> adminService.suspendUser(1L, request)
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Suspend User Should Throw Exception When Soft Duration Is Null")
    void suspendUser_shouldThrowExceptionWhenDurationIsNull() {

        SuspendUserRequest request = new SuspendUserRequest();
        request.setSuspensionType(SuspensionType.SOFT);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        assertThrows(
                BadRequestException.class,
                () -> adminService.suspendUser(1L, request)
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }

    @Test
    @DisplayName("Suspend User Should Throw Exception When Duration Less Than One")
    void suspendUser_shouldThrowExceptionWhenDurationLessThanOne() {

        SuspendUserRequest request = new SuspendUserRequest();
        request.setSuspensionType(SuspensionType.SOFT);
        request.setDurationInDays(0);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        assertThrows(
                BadRequestException.class,
                () -> adminService.suspendUser(1L, request)
        );
    }

    @Test
    @DisplayName("Suspend User Should Throw Exception When Duration Greater Than Seven")
    void suspendUser_shouldThrowExceptionWhenDurationGreaterThanSeven() {

        SuspendUserRequest request = new SuspendUserRequest();
        request.setSuspensionType(SuspensionType.SOFT);
        request.setDurationInDays(8);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        assertThrows(
                BadRequestException.class,
                () -> adminService.suspendUser(1L, request)
        );
    }

    @Test
    @DisplayName("Suspend User Should Call Audit Log")
    void suspendUser_shouldCallAuditLog() {

        SuspendUserRequest request = new SuspendUserRequest();
        request.setSuspensionType(SuspensionType.HARD);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        adminService.suspendUser(1L, request);

        verify(auditLogService, times(1))
                .logAction(
                        eq(AuditAction.USER_SUSPENDED),
                        eq(admin),
                        eq(user),
                        eq("HARD")
                );
    }

    @Test
    @DisplayName("Unsuspend User Should Unsuspend Successfully")
    void unsuspendUser_shouldUnsuspendSuccessfully() {

        user.setSuspended(true);
        user.setSuspensionType(SuspensionType.HARD);
        user.setSuspendedAt(LocalDateTime.now());
        user.setSuspendedBy(admin);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        adminService.unsuspendUser(1L);

        assertFalse(user.isSuspended());
        assertNull(user.getSuspensionType());
        assertNull(user.getSuspendedUntil());
        assertNull(user.getSuspendedAt());
        assertNull(user.getSuspendedBy());

        verify(auditLogService).logAction(
                eq(AuditAction.USER_UNSUSPENDED),
                eq(admin),
                eq(user),
                eq("Suspension removed")
        );
    }

    @Test
    @DisplayName("Unsuspend User Should Throw Exception When User Is Not Suspended")
    void unsuspendUser_shouldThrowExceptionWhenUserIsNotSuspended() {

        user.setSuspended(false);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        assertThrows(
                BadRequestException.class,
                () -> adminService.unsuspendUser(1L)
        );

        verify(auditLogService, never())
                .logAction(
                        any(),
                        any(),
                        any(),
                        anyString()
                );
    }
    @Test
    @DisplayName("Unsuspend User Should Call Audit Log")
    void unsuspendUser_shouldCallAuditLog() {

        user.setSuspended(true);
        user.setSuspensionType(SuspensionType.HARD);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userService.getCurrentUser())
                .thenReturn(admin);

        adminService.unsuspendUser(1L);

        verify(auditLogService, times(1))
                .logAction(
                        eq(AuditAction.USER_UNSUSPENDED),
                        eq(admin),
                        eq(user),
                        eq("Suspension removed")
                );
    }

    @Test
    @DisplayName("Get Suspended Users Should Return Suspended Users")
    void getSuspendedUsers_shouldReturnSuspendedUsers() {

        user.setSuspended(true);
        user.setSuspensionType(SuspensionType.HARD);
        user.setSuspendedAt(LocalDateTime.now());
        user.setSuspendedBy(admin);

        when(userRepository.findBySuspendedTrue())
                .thenReturn(List.of(user));

        List<SuspendedUserResponse> response =
                adminService.getSuspendedUsers();

        assertEquals(1, response.size());

        assertEquals(
                user.getId(),
                response.get(0).getId()
        );

        assertEquals(
                SuspensionType.HARD,
                response.get(0).getSuspensionType()
        );

        assertEquals(
                admin.getEmail(),
                response.get(0).getSuspendedBy()
        );

        verify(userRepository)
                .findBySuspendedTrue();
    }

    @Test
    @DisplayName("Get Suspended Users Should Return Empty List")
    void getSuspendedUsers_shouldReturnEmptyList() {

        when(userRepository.findBySuspendedTrue())
                .thenReturn(List.of());

        List<SuspendedUserResponse> response =
                adminService.getSuspendedUsers();

        assertTrue(response.isEmpty());

        verify(userRepository)
                .findBySuspendedTrue();
    }

    @Test
    @DisplayName("Get Dashboard Stats Should Return Correct Statistics")
    void getDashboardStats_shouldReturnCorrectStatistics() {

        when(userRepository.count())
                .thenReturn(20L);

        when(userRepository.countByRole(Role.ADMIN))
                .thenReturn(2L);

        when(userRepository.countByRole(Role.ACTING_ADMIN))
                .thenReturn(3L);

        when(userRepository.countByMarkedTrue())
                .thenReturn(4L);

        when(userRepository.countBySuspendedTrue())
                .thenReturn(5L);

        when(urlRepository.count())
                .thenReturn(100L);

        when(urlRepository.getTotalClicks())
                .thenReturn(1500L);

        AdminDashboardResponse response =
                adminService.getDashboardStats();

        assertEquals(20L, response.getTotalUsers());
        assertEquals(2L, response.getTotalAdmins());
        assertEquals(3L, response.getTotalActingAdmins());
        assertEquals(4L, response.getTotalMarkedUsers());
        assertEquals(5L, response.getTotalSuspendedUsers());
        assertEquals(100L, response.getTotalUrls());
        assertEquals(1500L, response.getTotalClicks());
    }
    @Test
    @DisplayName("Search Users By Name Should Return Matching Users")
    void searchUsersByName_shouldReturnMatchingUsers() {

        Page<User> page = new PageImpl<>(List.of(user));

        when(userRepository.findByNameContainingIgnoreCase(
                eq("Rahul"),
                any()))
                .thenReturn(page);

        Page<AdminUserResponse> response =
                adminService.searchUsersByName(
                        "Rahul",
                        0,
                        10
                );

        assertEquals(1, response.getTotalElements());
        assertEquals(
                "Rahul",
                response.getContent().get(0).getName()
        );
    }

    @Test
    @DisplayName("Search Users By Email Should Return Matching Users")
    void searchUsersByEmail_shouldReturnMatchingUsers() {

        Page<User> page = new PageImpl<>(List.of(user));

        when(userRepository.findByEmailContainingIgnoreCase(
                eq("rahul"),
                any()))
                .thenReturn(page);

        Page<AdminUserResponse> response =
                adminService.searchUsersByEmail(
                        "rahul",
                        0,
                        10
                );

        assertEquals(1, response.getTotalElements());

        assertEquals(
                "rahul@gmail.com",
                response.getContent().get(0).getEmail()
        );
    }

    @Test
    @DisplayName("Filter Users By Role Should Return Matching Users")
    void filterUsersByRole_shouldReturnMatchingUsers() {

        Page<User> page = new PageImpl<>(List.of(user));

        when(userRepository.findByRole(
                eq(Role.USER),
                any()))
                .thenReturn(page);

        Page<AdminUserResponse> response =
                adminService.filterUsersByRole(
                        Role.USER,
                        0,
                        10
                );

        assertEquals(1, response.getTotalElements());

        assertEquals(
                Role.USER,
                response.getContent().get(0).getRole()
        );
    }

    @Test
    @DisplayName("Filter Marked Users Should Return Matching Users")
    void filterMarkedUsers_shouldReturnMatchingUsers() {

        user.setMarked(true);

        Page<User> page = new PageImpl<>(List.of(user));

        when(userRepository.findByMarked(
                eq(true),
                any()))
                .thenReturn(page);

        Page<AdminUserResponse> response =
                adminService.filterMarkedUsers(
                        true,
                        0,
                        10
                );

        assertEquals(1, response.getTotalElements());

        assertTrue(
                response.getContent().get(0).isMarked()
        );
    }

    @Test
    @DisplayName("Filter Suspended Users Should Return Matching Users")
    void filterSuspendedUsers_shouldReturnMatchingUsers() {

        user.setSuspended(true);

        Page<User> page = new PageImpl<>(List.of(user));

        when(userRepository.findBySuspended(
                eq(true),
                any()))
                .thenReturn(page);

        Page<AdminUserResponse> response =
                adminService.filterSuspendedUsers(
                        true,
                        0,
                        10
                );

        assertEquals(1, response.getTotalElements());

        assertTrue(
                response.getContent().get(0).isSuspended()
        );
    }
}