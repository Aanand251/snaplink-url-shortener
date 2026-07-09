package com.anand.url_shortner.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private static final String SECRET =
            "VGhpc0lzQVN1cGVyU2VjcmV0S2V5Rm9ySldUVG9rZW5HZW5lcmF0aW9uMTIzNDU2";

    @BeforeEach
    void setUp() {

        jwtService = new JwtService();

        ReflectionTestUtils.setField(
                jwtService,
                "secret",
                SECRET
        );

        jwtService.init();
    }

    @Test
    @DisplayName("Generate Token Should Return Valid JWT")
    void generateToken_shouldReturnToken() {

        String token =
                jwtService.generateToken("anand@gmail.com");

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    @DisplayName("Extract Email Should Return Correct Email")
    void extractEmail_shouldReturnCorrectEmail() {

        String token =
                jwtService.generateToken("anand@gmail.com");

        String email =
                jwtService.extractEmail(token);

        assertEquals(
                "anand@gmail.com",
                email
        );
    }

    @Test
    @DisplayName("Valid Token Should Return True")
    void isTokenValid_shouldReturnTrue() {

        String token =
                jwtService.generateToken("anand@gmail.com");

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
                jwtService.generateToken("anand@gmail.com");

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
                jwtService.generateToken("anand@gmail.com");

        String subject =
                jwtService.extractClaim(
                        token,
                        claims -> claims.getSubject()
                );

        assertEquals(
                "anand@gmail.com",
                subject
        );
    }

}