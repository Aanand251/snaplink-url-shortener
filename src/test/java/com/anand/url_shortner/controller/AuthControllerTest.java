package com.anand.url_shortner.controller;

import com.anand.url_shortner.auth.AuthenticationService;
import com.anand.url_shortner.dto.LoginRequest;
import com.anand.url_shortner.dto.LoginResponse;
import com.anand.url_shortner.dto.RegisterRequest;
import com.anand.url_shortner.dto.RegisterResponse;
import com.anand.url_shortner.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.anand.url_shortner.config.SecurityConfig;


@WebMvcTest(
        controllers = AuthController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                ),
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = com.anand.url_shortner.auth.JwtFilter.class
                ),
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = com.anand.url_shortner.filter.RateLimitFilter.class
                )
        }
)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("Application Context Should Load")
    void contextLoads() {

    }

    @Test
    @DisplayName("Register API Should Return 201")
    void register_shouldReturnCreated() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setName("Anand");
        request.setEmail("anand@gmail.com");
        request.setPassword("password123");

        RegisterResponse response = RegisterResponse.builder()
                .success(true)
                .message("User Registered Successfully")
                .build();

        when(userService.register(any(RegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("User Registered Successfully"));
    }

    @Test
    @DisplayName("Login API Should Return 200")
    void login_shouldReturnOk() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail("anand@gmail.com");
        request.setPassword("password123");
        LoginResponse response = LoginResponse.builder()
                .success(true)
                .message("Login Successful")
                .token("dummy-jwt-token")
                .build();

        when(authenticationService.login(any(LoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("Login Successful"))
                .andExpect(jsonPath("$.token")
                        .value("dummy-jwt-token"));
    }


    @Test
    @DisplayName("Me Endpoint Should Return Success")
    void me_shouldReturnAuthenticatedMessage() throws Exception {

        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isOk())
                .andExpect(content().string("Authenticated Successfully"));
    }

}