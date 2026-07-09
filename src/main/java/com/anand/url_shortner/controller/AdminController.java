package com.anand.url_shortner.controller;

import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(
        name = "Admin Controller",
        description = "Admin APIs"
)
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Get All Users")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(
                adminService.getAllUsers()
        );
    }

    @Operation(summary = "Get All URLs")
    @GetMapping("/urls")
    public ResponseEntity<List<UrlMapping>> getAllUrls() {

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