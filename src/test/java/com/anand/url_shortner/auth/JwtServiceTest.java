package com.anand.url_shortner.auth;

import com.anand.url_shortner.entity.Role;
import com.anand.url_shortner.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private static final String SECRET =
            "VGhpc0lzQVN1cGVyU2VjcmV0S2V5Rm9ySldUVG9rZW5HZW5lcmF0aW9uMTIzNDU2";

    private User user;

    @BeforeEach
    void setUp() {

        jwtService = new JwtService();

        ReflectionTestUtils.setField(
                jwtService,
                "secret",
                SECRET
        );

        jwtService.init();

        user = new User();
        user.setEmail("anand@gmail.com");
        user.setRole(Role.USER);
    }

    @Test
    @DisplayName("Generate Token Should Return Valid JWT")
    void generateToken_shouldReturnToken() {

        String token =
                jwtService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    @DisplayName("Extract Email Should Return Correct Email")
    void extractEmail_shouldReturnCorrectEmail() {

        String token =
                jwtService.generateToken(user);

        String email =
                jwtService.extractEmail(token);

        assertEquals(
                "anand@gmail.com",
                email
        );
    }

    @Test
    @DisplayName("Extract Role Should Return Correct Role")
    void extractRole_shouldReturnCorrectRole() {

        String token =
                jwtService.generateToken(user);

        String role =
                jwtService.extractRole(token);

        assertEquals(
                "USER",
                role
        );
    }

    @Test
    @DisplayName("Valid Token Should Return True")
    void isTokenValid_shouldReturnTrue() {

        String token =
                jwtService.generateToken(user);

        assertTrue(
                jwtService.isTokenValid(
                        token,
                        "anand@gmail.com"
                )
        );
    }

    @Test
    @DisplayName("Wrong Email Should Return False")
    void isTokenValid_shouldReturnFalseForWrongEmail() {

        String token =
                jwtService.generateToken(user);

        assertFalse(
                jwtService.isTokenValid(
                        token,
                        "abc@gmail.com"
                )
        );
    }

    @Test
    @DisplayName("Extract Claim Should Return Subject")
    void extractClaim_shouldReturnSubject() {

        String token =
                jwtService.generateToken(user);

        String subject =
                jwtService.extractClaim(
                        token,
                        Claims::getSubject
                );

        assertEquals(
                "anand@gmail.com",
                subject
        );
    }
}