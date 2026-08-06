package com.anand.url_shortner.config;

import com.anand.url_shortner.auth.CustomUserDetailsService;
import com.anand.url_shortner.auth.JwtFilter;
import com.anand.url_shortner.filter.RateLimitFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {

        JwtFilter jwtFilter =
                mock(JwtFilter.class);

        RateLimitFilter rateLimitFilter =
                mock(RateLimitFilter.class);

        securityConfig =
                new SecurityConfig(
                        jwtFilter,
                        rateLimitFilter
                );

        ReflectionTestUtils.setField(
                securityConfig,
                "allowedOrigins",
                "http://localhost:5173"
        );
    }

    @Test
    @DisplayName("Should Allow Frontend Origin")
    void corsConfiguration_shouldAllowFrontendOrigin() {

        CorsConfiguration configuration =
                getCorsConfiguration();

        assertNotNull(configuration);
        assertNotNull(configuration.getAllowedOrigins());

        assertTrue(
                configuration
                        .getAllowedOrigins()
                        .contains("http://localhost:5173")
        );
    }

    @Test
    @DisplayName("Should Not Allow Unknown Origin")
    void corsConfiguration_shouldNotAllowUnknownOrigin() {

        CorsConfiguration configuration =
                getCorsConfiguration();

        assertNotNull(configuration);
        assertNotNull(configuration.getAllowedOrigins());

        assertFalse(
                configuration
                        .getAllowedOrigins()
                        .contains("http://malicious-site.com")
        );
    }

    @Test
    @DisplayName("Should Allow Required HTTP Methods")
    void corsConfiguration_shouldAllowRequiredHttpMethods() {

        CorsConfiguration configuration =
                getCorsConfiguration();

        assertNotNull(configuration);
        assertNotNull(configuration.getAllowedMethods());

        assertTrue(
                configuration
                        .getAllowedMethods()
                        .contains("GET")
        );

        assertTrue(
                configuration
                        .getAllowedMethods()
                        .contains("POST")
        );

        assertTrue(
                configuration
                        .getAllowedMethods()
                        .contains("PUT")
        );

        assertTrue(
                configuration
                        .getAllowedMethods()
                        .contains("PATCH")
        );

        assertTrue(
                configuration
                        .getAllowedMethods()
                        .contains("DELETE")
        );

        assertTrue(
                configuration
                        .getAllowedMethods()
                        .contains("OPTIONS")
        );
    }

    @Test
    @DisplayName("Should Allow Required Headers")
    void corsConfiguration_shouldAllowRequiredHeaders() {

        CorsConfiguration configuration =
                getCorsConfiguration();

        assertNotNull(configuration);
        assertNotNull(configuration.getAllowedHeaders());

        assertTrue(
                configuration
                        .getAllowedHeaders()
                        .contains("Authorization")
        );

        assertTrue(
                configuration
                        .getAllowedHeaders()
                        .contains("Content-Type")
        );
    }

    @Test
    @DisplayName("Should Allow Credentials")
    void corsConfiguration_shouldAllowCredentials() {

        CorsConfiguration configuration =
                getCorsConfiguration();

        assertNotNull(configuration);

        assertEquals(Boolean.TRUE, configuration.getAllowCredentials());
    }

    private CorsConfiguration getCorsConfiguration() {

        CorsConfigurationSource source =
                securityConfig.corsConfigurationSource();

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.setRequestURI("/api/auth/login");

        return source.getCorsConfiguration(request);
    }

    @Test
    @DisplayName("Should Allow Multiple Frontend Origins")
    void corsConfiguration_shouldAllowMultipleOrigins() {

        ReflectionTestUtils.setField(
                securityConfig,
                "allowedOrigins",
                "http://localhost:5173,http://localhost:3000"
        );

        CorsConfiguration configuration =
                getCorsConfiguration();

        assertNotNull(configuration);
        assertNotNull(configuration.getAllowedOrigins());

        assertTrue(
                configuration.getAllowedOrigins()
                        .contains("http://localhost:5173")
        );

        assertTrue(
                configuration.getAllowedOrigins()
                        .contains("http://localhost:3000")
        );
    }

    @Test
    @DisplayName("Should Ignore Blank Origins")
    void corsConfiguration_shouldIgnoreBlankOrigins() {

        ReflectionTestUtils.setField(
                securityConfig,
                "allowedOrigins",
                "http://localhost:5173, ,   ,http://localhost:3000"
        );

        CorsConfiguration configuration =
                getCorsConfiguration();

        assertNotNull(configuration);

        assertEquals(
                2,
                configuration.getAllowedOrigins().size()
        );

        assertFalse(
                configuration.getAllowedOrigins()
                        .contains("")
        );
    }

    @Test
    @DisplayName("Should Create Authentication Manager")
    void authenticationManager_shouldCreateBean() {

        CustomUserDetailsService userDetailsService =
                mock(CustomUserDetailsService.class);

        PasswordEncoder passwordEncoder =
                mock(PasswordEncoder.class);

        AuthenticationManager authenticationManager =
                securityConfig.authenticationManager(
                        userDetailsService,
                        passwordEncoder
                );

        assertNotNull(authenticationManager);
        assertInstanceOf(ProviderManager.class, authenticationManager);
    }
}