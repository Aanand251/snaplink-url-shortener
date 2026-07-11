package com.anand.url_shortner.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {

        globalExceptionHandler =
                new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Should Return 400 For Validation Exception")
    void handleValidation_shouldReturn400() {

        MethodArgumentNotValidException exception =
                mock(MethodArgumentNotValidException.class);

        BindingResult bindingResult =
                mock(BindingResult.class);

        FieldError fieldError =
                new FieldError(
                        "request",
                        "originalUrl",
                        "Original URL is required"
                );

        when(exception.getBindingResult())
                .thenReturn(bindingResult);

        when(bindingResult.getFieldError())
                .thenReturn(fieldError);

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler
                        .handleValidation(exception);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertEquals(
                HttpStatus.BAD_REQUEST.value(),
                response.getBody().getStatus()
        );

        assertEquals(
                "Original URL is required",
                response.getBody().getMessage()
        );
    }

    @Test
    @DisplayName("Should Return 404 For URL Not Found")
    void handleUrlNotFound_shouldReturn404() {

        UrlNotFoundException exception =
                new UrlNotFoundException("github");

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler
                        .handleUrlNotFound(exception);

        assertEquals(
                HttpStatus.NOT_FOUND,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertEquals(
                HttpStatus.NOT_FOUND.value(),
                response.getBody().getStatus()
        );

        assertEquals(
                "URL not found for short code: github",
                response.getBody().getMessage()
        );
    }

    @Test
    @DisplayName("Should Return 403 For Resource Access Denied")
    void handleAccessDenied_shouldReturn403() {

        ResourceAccessDeniedException exception =
                new ResourceAccessDeniedException();

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler
                        .handleAccessDenied(exception);

        assertEquals(
                HttpStatus.FORBIDDEN,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertEquals(
                HttpStatus.FORBIDDEN.value(),
                response.getBody().getStatus()
        );

        assertEquals(
                exception.getMessage(),
                response.getBody().getMessage()
        );
    }
    @Test
    @DisplayName("Should Return 410 For Expired URL")
    void handleUrlExpired_shouldReturn410() {

        UrlExpiredException exception =
                new UrlExpiredException("github");

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler
                        .handleUrlExpired(exception);

        assertEquals(
                HttpStatus.GONE,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertEquals(
                HttpStatus.GONE.value(),
                response.getBody().getStatus()
        );

        assertEquals(
                "URL has expired: github",
                response.getBody().getMessage()
        );
    }

    @Test
    @DisplayName("Should Return 400 For Illegal Argument")
    void handleIllegalArgument_shouldReturn400() {

        IllegalArgumentException exception =
                new IllegalArgumentException(
                        "Invalid argument"
                );

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler
                        .handleIllegalArgument(exception);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertEquals(
                HttpStatus.BAD_REQUEST.value(),
                response.getBody().getStatus()
        );

        assertEquals(
                "Invalid argument",
                response.getBody().getMessage()
        );
    }
}