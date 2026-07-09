package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.RegisterRequest;
import com.anand.url_shortner.dto.RegisterResponse;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    private RegisterRequest registerRequest;
    private User user;

    @BeforeEach
    void setUp() {

        registerRequest = new RegisterRequest();
        registerRequest.setName("Anand");
        registerRequest.setEmail("anand@gmail.com");
        registerRequest.setPassword("password123");

        user = User.builder()
                .name("Anand")
                .email("anand@gmail.com")
                .password("encoded-password")
                .build();
    }

    @Test
    @DisplayName("Register Should Save User Successfully")
    void register_shouldSaveUser() {

        when(userRepository.existsByEmail("anand@gmail.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("password123"))
                .thenReturn("encoded-password");

        RegisterResponse response =
                userService.register(registerRequest);

        assertNotNull(response);
        assertTrue(response.isSuccess());

        assertEquals(
                "User registered successfully",
                response.getMessage()
        );

        verify(userRepository)
                .save(any(User.class));
    }

    @Test
    @DisplayName("Register Should Throw Exception When Email Exists")
    void register_shouldThrowExceptionWhenEmailExists() {

        when(userRepository.existsByEmail("anand@gmail.com"))
                .thenReturn(true);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> userService.register(registerRequest)
                );

        assertEquals(
                "Email already in use",
                exception.getMessage()
        );

        verify(userRepository, never())
                .save(any(User.class));
    }

    @Test
    @DisplayName("Get Current User Should Return User")
    void getCurrentUser_shouldReturnUser() {

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(authentication.getName())
                .thenReturn("anand@gmail.com");

        when(userRepository.findByEmail("anand@gmail.com"))
                .thenReturn(Optional.of(user));

        User result =
                userService.getCurrentUser();

        assertNotNull(result);

        assertEquals(
                "anand@gmail.com",
                result.getEmail()
        );
    }

    @Test
    @DisplayName("Get Current User Should Throw Exception")
    void getCurrentUser_shouldThrowException() {

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(authentication.getName())
                .thenReturn("anand@gmail.com");

        when(userRepository.findByEmail("anand@gmail.com"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> userService.getCurrentUser()
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }

}