package com.anand.url_shortner.controller;

import com.anand.url_shortner.auth.JwtFilter;
import com.anand.url_shortner.config.SecurityConfig;
import com.anand.url_shortner.dto.AdminUrlResponse;
import com.anand.url_shortner.dto.AdminUserResponse;
import com.anand.url_shortner.entity.Role;
import com.anand.url_shortner.filter.RateLimitFilter;
import com.anand.url_shortner.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

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

    @MockitoBean
    private AdminService adminService;

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
                .andExpect(jsonPath("$[0].role").value("USER"));

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
                .andExpect(jsonPath("$[0].shortCode").value("abcd"))
                .andExpect(jsonPath("$[0].totalClicks").value(10))
                .andExpect(jsonPath("$[0].userId").value(1))
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
    @DisplayName("Make Admin Should Return 200")
    void makeAdmin_shouldReturn200() throws Exception {

        doNothing()
                .when(adminService)
                .makeAdmin(1L);

        mockMvc.perform(put("/api/admin/users/1/admin"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("User promoted to ADMIN"));

        verify(adminService).makeAdmin(1L);
    }

    @Test
    @DisplayName("Make User Should Return 200")
    void makeUser_shouldReturn200() throws Exception {

        doNothing()
                .when(adminService)
                .makeUser(1L);

        mockMvc.perform(put("/api/admin/users/1/user"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("User role changed to USER"));

        verify(adminService).makeUser(1L);
    }
}