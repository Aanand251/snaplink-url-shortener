package com.anand.url_shortner.controller;
import com.anand.url_shortner.dto.*;
import com.anand.url_shortner.entity.AuditAction;
import com.anand.url_shortner.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.anand.url_shortner.dto.AdminDashboardResponse;
import java.util.List;
import com.anand.url_shortner.dto.AuditLogResponse;
import com.anand.url_shortner.service.AuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import com.anand.url_shortner.entity.Role;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(
        name = "Admin Controller",
        description = "Administrative APIs for user and URL management"
)
public class AdminController {

    private final AdminService adminService;
    private final AuditLogService auditLogService;

    @Operation(summary = "Get All Users")
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {

        return ResponseEntity.ok(
                adminService.getAllUsers()
        );
    }

    @Operation(summary = "Get Admin Dashboard Statistics")
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboardStats() {

        return ResponseEntity.ok(
                adminService.getDashboardStats()
        );
    }

    @Operation(summary = "Get All URLs")
    @GetMapping("/urls")
    public ResponseEntity<List<AdminUrlResponse>> getAllUrls() {

        return ResponseEntity.ok(
                adminService.getAllUrls()
        );
    }

    @Operation(summary = "Delete User")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id
    ) {

        adminService.deleteUser(id);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }

    @Operation(summary = "Change User Role")
    @PutMapping("/users/{id}/role")
    public ResponseEntity<String> changeUserRole(
            @PathVariable Long id,
            @RequestBody @Valid ChangeUserRoleRequest request
    ) {

        adminService.changeUserRole(id, request);

        return ResponseEntity.ok(
                "User role updated successfully."
        );
    }

    @Operation(summary = "Mark User")
    @PutMapping("/users/{id}/mark")
    public ResponseEntity<String> markUser(
            @PathVariable Long id,
            @RequestBody @Valid MarkUserRequest request
    ) {

        adminService.markUser(id, request);

        return ResponseEntity.ok(
                "User marked successfully."
        );
    }

    @Operation(summary = "Unmark User")
    @PutMapping("/users/{id}/unmark")
    public ResponseEntity<String> unmarkUser(
            @PathVariable Long id
    ) {

        adminService.unmarkUser(id);

        return ResponseEntity.ok(
                "User unmarked successfully."
        );
    }

    @Operation(summary = "Get Marked Users")
    @GetMapping("/users/marked")
    public ResponseEntity<List<MarkedUserResponse>> getMarkedUsers() {

        return ResponseEntity.ok(
                adminService.getMarkedUsers()
        );
    }

    @Operation(summary = "Suspend User")
    @PutMapping("/users/{id}/suspend")
    public ResponseEntity<String> suspendUser(
            @PathVariable Long id,
            @RequestBody @Valid SuspendUserRequest request
    ) {

        adminService.suspendUser(id, request);

        return ResponseEntity.ok(
                "User suspended successfully."
        );
    }

    @Operation(summary = "Unsuspend User")
    @PutMapping("/users/{id}/unsuspend")
    public ResponseEntity<String> unsuspendUser(
            @PathVariable Long id
    ) {

        adminService.unsuspendUser(id);

        return ResponseEntity.ok(
                "User unsuspended successfully."
        );
    }

    @Operation(summary = "Get Suspended Users")
    @GetMapping("/users/suspended")
    public ResponseEntity<List<SuspendedUserResponse>> getSuspendedUsers() {

        return ResponseEntity.ok(
                adminService.getSuspendedUsers()
        );
    }
    @Operation(summary = "Get Audit Logs")
    @GetMapping("/audit-logs")
    public ResponseEntity<Page<AuditLogResponse>> getAuditLogs(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogs(page, size)
        );
    }

    @Operation(summary = "Get Audit Logs By Action")
    @GetMapping("/audit-logs/action")
    public ResponseEntity<Page<AuditLogResponse>> getLogsByAction(

            @RequestParam AuditAction action,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                auditLogService.getLogsByAction(
                        action,
                        page,
                        size
                )
        );
    }

    @Operation(summary = "Get Audit Logs By Admin")
    @GetMapping("/audit-logs/admin")
    public ResponseEntity<Page<AuditLogResponse>> getLogsByAdmin(

            @RequestParam String email,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                auditLogService.getLogsByAdminEmail(
                        email,
                        page,
                        size
                )
        );
    }

    @Operation(summary = "Get Audit Logs By Target User")
    @GetMapping("/audit-logs/user")
    public ResponseEntity<Page<AuditLogResponse>> getLogsByTargetUser(

            @RequestParam String email,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                auditLogService.getLogsByTargetUserEmail(
                        email,
                        page,
                        size
                )
        );
    }
    @Operation(summary = "Search Users By Name")
    @GetMapping("/users/search/name")
    public ResponseEntity<Page<AdminUserResponse>> searchUsersByName(

            @RequestParam String name,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                adminService.searchUsersByName(
                        name,
                        page,
                        size
                )
        );
    }

    @Operation(summary = "Search Users By Email")
    @GetMapping("/users/search/email")
    public ResponseEntity<Page<AdminUserResponse>> searchUsersByEmail(

            @RequestParam String email,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                adminService.searchUsersByEmail(
                        email,
                        page,
                        size
                )
        );
    }

    @Operation(summary = "Filter Users By Role")
    @GetMapping("/users/filter/role")
    public ResponseEntity<Page<AdminUserResponse>> filterUsersByRole(

            @RequestParam Role role,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                adminService.filterUsersByRole(
                        role,
                        page,
                        size
                )
        );
    }

    @Operation(summary = "Filter Marked Users")
    @GetMapping("/users/filter/marked")
    public ResponseEntity<Page<AdminUserResponse>> filterMarkedUsers(

            @RequestParam boolean marked,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                adminService.filterMarkedUsers(
                        marked,
                        page,
                        size
                )
        );
    }

    @Operation(summary = "Filter Suspended Users")
    @GetMapping("/users/filter/suspended")
    public ResponseEntity<Page<AdminUserResponse>> filterSuspendedUsers(

            @RequestParam boolean suspended,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                adminService.filterSuspendedUsers(
                        suspended,
                        page,
                        size
                )
        );
    }
}