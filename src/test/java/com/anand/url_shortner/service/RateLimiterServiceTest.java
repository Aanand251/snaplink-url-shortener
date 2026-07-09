package com.anand.url_shortner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateLimiterServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private RateLimiterService rateLimiterService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);
    }

    @Test
    @DisplayName("First Request Should Be Allowed")
    void shouldAllowFirstRequest() {

        when(valueOperations.increment("rate_limit:127.0.0.1"))
                .thenReturn(1L);

        when(redisTemplate.expire(
                eq("rate_limit:127.0.0.1"),
                any(Duration.class)))
                .thenReturn(true);

        boolean result =
                rateLimiterService.isAllowed("127.0.0.1");

        assertTrue(result);

        verify(redisTemplate)
                .expire(
                        eq("rate_limit:127.0.0.1"),
                        any(Duration.class)
                );
    }

    @Test
    @DisplayName("Request Within Limit Should Be Allowed")
    void shouldAllowRequestWithinLimit() {

        when(valueOperations.increment("rate_limit:127.0.0.1"))
                .thenReturn(50L);

        boolean result =
                rateLimiterService.isAllowed("127.0.0.1");

        assertTrue(result);

        verify(redisTemplate, never())
                .expire(anyString(), any(Duration.class));
    }

    @Test
    @DisplayName("Request Above Limit Should Be Blocked")
    void shouldBlockRequestAboveLimit() {

        when(valueOperations.increment("rate_limit:127.0.0.1"))
                .thenReturn(101L);

        boolean result =
                rateLimiterService.isAllowed("127.0.0.1");

        assertFalse(result);
    }

    @Test
    @DisplayName("Null Response From Redis Should Return False")
    void shouldReturnFalseWhenRedisReturnsNull() {

        when(valueOperations.increment("rate_limit:127.0.0.1"))
                .thenReturn(null);

        boolean result =
                rateLimiterService.isAllowed("127.0.0.1");

        assertFalse(result);
    }
}