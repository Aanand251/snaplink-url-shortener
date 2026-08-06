package com.anand.url_shortner.controller;

import com.anand.url_shortner.auth.JwtFilter;
import com.anand.url_shortner.config.SecurityConfig;
import com.anand.url_shortner.dto.*;
import com.anand.url_shortner.entity.AuditAction;
import com.anand.url_shortner.entity.MarkReason;
import com.anand.url_shortner.entity.Role;
import com.anand.url_shortner.entity.SuspensionType;
import com.anand.url_shortner.filter.RateLimitFilter;
import com.anand.url_shortner.service.AdminService;
import com.anand.url_shortner.service.AuditLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        controllers = AdminController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                ),
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = JwtFilter.class
                ),
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = RateLimitFilter.class
                )
        }
)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AdminService adminService;

    @MockitoBean
    private AuditLogService auditLogService;

    @Test
    @DisplayName("Application Context Should Load")
    void contextLoads() {

    }

    @Test
    @DisplayName("Get All Users Should Return 200")
    void getAllUsers_shouldReturn200() throws Exception {

        AdminUserResponse response = AdminUserResponse.builder()
                .id(1L)
                .name("Rahul")
                .email("rahul@gmail.com")
                .role(Role.USER)
                .totalLinks(5L)
                .totalClicks(100L)
                .marked(false)
                .suspended(false)
                .createdAt(LocalDateTime.now())
                .build();

        when(adminService.getAllUsers())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Rahul"))
                .andExpect(jsonPath("$[0].email")
                        .value("rahul@gmail.com"))
                .andExpect(jsonPath("$[0].role")
                        .value("USER"));

        verify(adminService).getAllUsers();
    }

    @Test
    @DisplayName("Get All URLs Should Return 200")
    void getAllUrls_shouldReturn200() throws Exception {

        AdminUrlResponse response = AdminUrlResponse.builder()
                .id(1L)
                .originalUrl("https://google.com")
                .shortCode("abcd")
                .totalClicks(10L)
                .createdAt(LocalDateTime.now())
                .userId(1L)
                .userEmail("rahul@gmail.com")
                .build();

        when(adminService.getAllUrls())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/admin/urls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].originalUrl")
                        .value("https://google.com"))
                .andExpect(jsonPath("$[0].shortCode")
                        .value("abcd"))
                .andExpect(jsonPath("$[0].totalClicks")
                        .value(10))
                .andExpect(jsonPath("$[0].userId")
                        .value(1))
                .andExpect(jsonPath("$[0].userEmail")
                        .value("rahul@gmail.com"));

        verify(adminService).getAllUrls();
    }

    @Test
    @DisplayName("Delete User Should Return 200")
    void deleteUser_shouldReturn200() throws Exception {

        doNothing()
                .when(adminService)
                .deleteUser(1L);

        mockMvc.perform(delete("/api/admin/users/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("User deleted successfully"));

        verify(adminService).deleteUser(1L);
    }

    @Test
    @DisplayName("Change User Role Should Return 200")
    void changeUserRole_shouldReturn200() throws Exception {

        ChangeUserRoleRequest request =
                new ChangeUserRoleRequest();

        request.setRole(Role.ACTING_ADMIN);

        doNothing()
                .when(adminService)
                .changeUserRole(eq(1L), refEq(request));

        mockMvc.perform(
                        put("/api/admin/users/1/role")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("User role updated successfully."));

        verify(adminService)
                .changeUserRole(eq(1L), refEq(request));
    }

    @Test
    @DisplayName("Get Dashboard Stats Should Return 200")
    void getDashboardStats_shouldReturn200() throws Exception {

        AdminDashboardResponse response =
                AdminDashboardResponse.builder()
                        .totalUsers(100L)
                        .totalAdmins(2L)
                        .totalActingAdmins(3L)
                        .totalMarkedUsers(5L)
                        .totalSuspendedUsers(4L)
                        .totalUrls(250L)
                        .totalClicks(5000L)
                        .build();

        when(adminService.getDashboardStats())
                .thenReturn(response);

        mockMvc.perform(get("/api/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUsers").value(100))
                .andExpect(jsonPath("$.totalAdmins").value(2))
                .andExpect(jsonPath("$.totalActingAdmins").value(3))
                .andExpect(jsonPath("$.totalMarkedUsers").value(5))
                .andExpect(jsonPath("$.totalSuspendedUsers").value(4))
                .andExpect(jsonPath("$.totalUrls").value(250))
                .andExpect(jsonPath("$.totalClicks").value(5000));

        verify(adminService).getDashboardStats();
    }

    @Test
    @DisplayName("Mark User Should Return 200")
    void markUser_shouldReturn200() throws Exception {

        MarkUserRequest request = new MarkUserRequest();
        request.setReason(MarkReason.SPAM);

        doNothing()
                .when(adminService)
                .markUser(eq(1L), refEq(request));

        mockMvc.perform(
                        put("/api/admin/users/1/mark")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("User marked successfully."));

        verify(adminService)
                .markUser(eq(1L), refEq(request));
    }

    @Test
    @DisplayName("Unmark User Should Return 200")
    void unmarkUser_shouldReturn200() throws Exception {

        doNothing()
                .when(adminService)
                .unmarkUser(1L);

        mockMvc.perform(
                        put("/api/admin/users/1/unmark")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("User unmarked successfully."));

        verify(adminService)
                .unmarkUser(1L);
    }

    @Test
    @DisplayName("Get Marked Users Should Return 200")
    void getMarkedUsers_shouldReturn200() throws Exception {

        MarkedUserResponse response =
                MarkedUserResponse.builder()
                        .id(1L)
                        .name("Rahul")
                        .email("rahul@gmail.com")
                        .reason(MarkReason.SPAM)
                        .markedBy("admin@gmail.com")
                        .markedAt(LocalDateTime.now())
                        .build();

        when(adminService.getMarkedUsers())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/admin/users/marked"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Rahul"))
                .andExpect(jsonPath("$[0].email").value("rahul@gmail.com"))
                .andExpect(jsonPath("$[0].reason").value("SPAM"));

        verify(adminService)
                .getMarkedUsers();
    }

    @Test
    @DisplayName("Suspend User Should Return 200")
    void suspendUser_shouldReturn200() throws Exception {

        SuspendUserRequest request = new SuspendUserRequest();
        request.setSuspensionType(SuspensionType.SOFT);
        request.setDurationInDays(5);

        doNothing()
                .when(adminService)
                .suspendUser(eq(1L), refEq(request));

        mockMvc.perform(
                        put("/api/admin/users/1/suspend")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("User suspended successfully."));

        verify(adminService)
                .suspendUser(eq(1L), refEq(request));
    }

    @Test
    @DisplayName("Unsuspend User Should Return 200")
    void unsuspendUser_shouldReturn200() throws Exception {

        doNothing()
                .when(adminService)
                .unsuspendUser(1L);

        mockMvc.perform(
                        put("/api/admin/users/1/unsuspend")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("User unsuspended successfully."));

        verify(adminService)
                .unsuspendUser(1L);
    }

    @Test
    @DisplayName("Get Suspended Users Should Return 200")
    void getSuspendedUsers_shouldReturn200() throws Exception {

        SuspendedUserResponse response =
                SuspendedUserResponse.builder()
                        .id(1L)
                        .name("Rahul")
                        .email("rahul@gmail.com")
                        .suspensionType(SuspensionType.SOFT)
                        .suspendedBy("admin@gmail.com")
                        .suspendedAt(LocalDateTime.now())
                        .build();

        when(adminService.getSuspendedUsers())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/admin/users/suspended"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Rahul"))
                .andExpect(jsonPath("$[0].email").value("rahul@gmail.com"))
                .andExpect(jsonPath("$[0].suspensionType").value("SOFT"));

        verify(adminService).getSuspendedUsers();
    }

    @Test
    @DisplayName("Get Audit Logs Should Return 200")
    void getAuditLogs_shouldReturn200() throws Exception {

        Page<AuditLogResponse> page =
                new PageImpl<>(List.of(
                        AuditLogResponse.builder()
                                .id(1L)
                                .performedBy("admin@gmail.com")
                                .targetUser("rahul@gmail.com")
                                .remarks("Role Changed")
                                .build()
                ));

        when(auditLogService.getAuditLogs(0, 10))
                .thenReturn(page);

        mockMvc.perform(
                        get("/api/admin/audit-logs")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));

        verify(auditLogService)
                .getAuditLogs(0,10);
    }

    @Test
    @DisplayName("Get Audit Logs By Action Should Return 200")
    void getLogsByAction_shouldReturn200() throws Exception {

        Page<AuditLogResponse> page =
                new PageImpl<>(List.of());

        when(auditLogService.getLogsByAction(
                AuditAction.USER_MARKED,
                0,
                10
        )).thenReturn(page);

        mockMvc.perform(
                        get("/api/admin/audit-logs/action")
                                .param("action","USER_MARKED")
                )
                .andExpect(status().isOk());

        verify(auditLogService)
                .getLogsByAction(
                        AuditAction.USER_MARKED,
                        0,
                        10
                );
    }

    @Test
    @DisplayName("Get Audit Logs By Admin Should Return 200")
    void getLogsByAdmin_shouldReturn200() throws Exception {

        Page<AuditLogResponse> page =
                new PageImpl<>(List.of());

        when(auditLogService.getLogsByAdminEmail(
                "admin@gmail.com",
                0,
                10
        )).thenReturn(page);

        mockMvc.perform(
                        get("/api/admin/audit-logs/admin")
                                .param("email","admin@gmail.com")
                )
                .andExpect(status().isOk());

        verify(auditLogService)
                .getLogsByAdminEmail(
                        "admin@gmail.com",
                        0,
                        10
                );
    }

    @Test
    @DisplayName("Get Audit Logs By Target User Should Return 200")
    void getLogsByTargetUser_shouldReturn200() throws Exception {

        Page<AuditLogResponse> page =
                new PageImpl<>(List.of());

        when(auditLogService.getLogsByTargetUserEmail(
                "rahul@gmail.com",
                0,
                10
        )).thenReturn(page);

        mockMvc.perform(
                        get("/api/admin/audit-logs/user")
                                .param("email","rahul@gmail.com")
                )
                .andExpect(status().isOk());

        verify(auditLogService)
                .getLogsByTargetUserEmail(
                        "rahul@gmail.com",
                        0,
                        10
                );
    }

    @Test
    @DisplayName("Search Users By Name Should Return 200")
    void searchUsersByName_shouldReturn200() throws Exception {

        AdminUserResponse response = AdminUserResponse.builder()
                .id(1L)
                .name("Rahul")
                .email("rahul@gmail.com")
                .role(Role.USER)
                .build();

        Page<AdminUserResponse> page =
                new PageImpl<>(List.of(response));

        when(adminService.searchUsersByName(
                "Rahul",
                0,
                10
        )).thenReturn(page);

        mockMvc.perform(
                        get("/api/admin/users/search/name")
                                .param("name", "Rahul")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name")
                        .value("Rahul"));

        verify(adminService)
                .searchUsersByName(
                        "Rahul",
                        0,
                        10
                );
    }

    @Test
    @DisplayName("Search Users By Email Should Return 200")
    void searchUsersByEmail_shouldReturn200() throws Exception {

        AdminUserResponse response = AdminUserResponse.builder()
                .id(1L)
                .email("rahul@gmail.com")
                .role(Role.USER)
                .build();

        Page<AdminUserResponse> page =
                new PageImpl<>(List.of(response));

        when(adminService.searchUsersByEmail(
                "rahul",
                0,
                10
        )).thenReturn(page);

        mockMvc.perform(
                        get("/api/admin/users/search/email")
                                .param("email", "rahul")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email")
                        .value("rahul@gmail.com"));

        verify(adminService)
                .searchUsersByEmail(
                        "rahul",
                        0,
                        10
                );
    }

    @Test
    @DisplayName("Filter Users By Role Should Return 200")
    void filterUsersByRole_shouldReturn200() throws Exception {

        AdminUserResponse response = AdminUserResponse.builder()
                .id(1L)
                .role(Role.USER)
                .build();

        Page<AdminUserResponse> page =
                new PageImpl<>(List.of(response));

        when(adminService.filterUsersByRole(
                Role.USER,
                0,
                10
        )).thenReturn(page);

        mockMvc.perform(
                        get("/api/admin/users/filter/role")
                                .param("role", "USER")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].role")
                        .value("USER"));

        verify(adminService)
                .filterUsersByRole(
                        Role.USER,
                        0,
                        10
                );
    }

    @Test
    @DisplayName("Filter Marked Users Should Return 200")
    void filterMarkedUsers_shouldReturn200() throws Exception {

        AdminUserResponse response = AdminUserResponse.builder()
                .id(1L)
                .marked(true)
                .build();

        Page<AdminUserResponse> page =
                new PageImpl<>(List.of(response));

        when(adminService.filterMarkedUsers(
                true,
                0,
                10
        )).thenReturn(page);

        mockMvc.perform(
                        get("/api/admin/users/filter/marked")
                                .param("marked", "true")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].marked")
                        .value(true));

        verify(adminService)
                .filterMarkedUsers(
                        true,
                        0,
                        10
                );
    }

    @Test
    @DisplayName("Filter Suspended Users Should Return 200")
    void filterSuspendedUsers_shouldReturn200() throws Exception {

        AdminUserResponse response = AdminUserResponse.builder()
                .id(1L)
                .suspended(true)
                .build();

        Page<AdminUserResponse> page =
                new PageImpl<>(List.of(response));

        when(adminService.filterSuspendedUsers(
                true,
                0,
                10
        )).thenReturn(page);

        mockMvc.perform(
                        get("/api/admin/users/filter/suspended")
                                .param("suspended", "true")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].suspended")
                        .value(true));

        verify(adminService)
                .filterSuspendedUsers(
                        true,
                        0,
                        10
                );
    }
}