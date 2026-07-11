package com.anand.url_shortner.controller;

import com.anand.url_shortner.dto.AdminUrlResponse;
import com.anand.url_shortner.dto.AdminUserResponse;
import com.anand.url_shortner.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(
        name = "Admin Controller",
        description = "Administrative APIs for user and URL management"
)
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Get All Users")
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {

        return ResponseEntity.ok(
                adminService.getAllUsers()
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

    @Operation(summary = "Promote User To Admin")
    @PutMapping("/users/{id}/admin")
    public ResponseEntity<String> makeAdmin(
            @PathVariable Long id
    ) {

        adminService.makeAdmin(id);

        return ResponseEntity.ok(
                "User promoted to ADMIN"
        );
    }

    @Operation(summary = "Demote Admin To User")
    @PutMapping("/users/{id}/user")
    public ResponseEntity<String> makeUser(
            @PathVariable Long id
    ) {

        adminService.makeUser(id);

        return ResponseEntity.ok(
                "User role changed to USER"
        );
    }
}