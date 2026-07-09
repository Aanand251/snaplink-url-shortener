package com.anand.url_shortner.auth;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Request Without Authorization Header Should Continue Filter Chain")
    void shouldContinueWhenAuthorizationHeaderMissing() throws Exception {

        jwtFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Request With Invalid Authorization Header Should Continue Filter Chain")
    void shouldContinueWhenAuthorizationHeaderInvalid() throws Exception {

        request.addHeader("Authorization", "InvalidToken");

        jwtFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Valid JWT Should Authenticate User")
    void shouldAuthenticateUserWhenTokenIsValid() throws Exception {

        String token = "dummy-token";
        String email = "anand@gmail.com";

        request.addHeader("Authorization", "Bearer " + token);

        User user = new User(
                email,
                "password",
                Collections.emptyList()
        );

        when(jwtService.extractEmail(token))
                .thenReturn(email);

        when(userDetailsService.loadUserByUsername(email))
                .thenReturn(user);

        when(jwtService.isTokenValid(token, email))
                .thenReturn(true);

        jwtFilter.doFilter(request, response, filterChain);

        verify(jwtService).extractEmail(token);

        verify(userDetailsService)
                .loadUserByUsername(email);

        verify(jwtService)
                .isTokenValid(token, email);

        verify(filterChain)
                .doFilter(request, response);
    }

    @Test
    @DisplayName("Invalid JWT Should Not Authenticate User")
    void shouldNotAuthenticateWhenTokenInvalid() throws Exception {

        String token = "dummy-token";
        String email = "anand@gmail.com";

        request.addHeader("Authorization", "Bearer " + token);

        User user = new User(
                email,
                "password",
                Collections.emptyList()
        );

        when(jwtService.extractEmail(token))
                .thenReturn(email);

        when(userDetailsService.loadUserByUsername(email))
                .thenReturn(user);

        when(jwtService.isTokenValid(token, email))
                .thenReturn(false);

        jwtFilter.doFilter(request, response, filterChain);

        verify(filterChain)
                .doFilter(request, response);
    }

}