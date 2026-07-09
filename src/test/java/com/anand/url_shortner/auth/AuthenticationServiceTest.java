package com.anand.url_shortner.auth;

import com.anand.url_shortner.dto.LoginRequest;
import com.anand.url_shortner.dto.LoginResponse;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {

        loginRequest = new LoginRequest();
        loginRequest.setEmail("anand@gmail.com");
        loginRequest.setPassword("password123");

        user = new User();
        user.setEmail("anand@gmail.com");
        user.setPassword("encoded-password");
    }

    @Test
    @DisplayName("Login Should Return JWT Token")
    void login_shouldReturnToken() {

        when(userRepository.findByEmail("anand@gmail.com"))
                .thenReturn(Optional.of(user));

        when(jwtService.generateToken("anand@gmail.com"))
                .thenReturn("dummy-jwt-token");

        LoginResponse response =
                authenticationService.login(loginRequest);

        assertNotNull(response);

        assertTrue(response.isSuccess());

        assertEquals(
                "Login Successful",
                response.getMessage()
        );

        assertEquals(
                "dummy-jwt-token",
                response.getToken()
        );

        verify(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        verify(jwtService)
                .generateToken("anand@gmail.com");
    }

    @Test
    @DisplayName("Should Throw Exception When User Not Found")
    void login_shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findByEmail("anand@gmail.com"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> authenticationService.login(loginRequest)
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Authentication Manager Should Be Invoked")
    void login_shouldAuthenticateUser() {

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        when(jwtService.generateToken(anyString()))
                .thenReturn("jwt-token");

        authenticationService.login(loginRequest);

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

}