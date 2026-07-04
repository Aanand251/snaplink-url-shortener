package com.anand.url_shortner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private static final int MAX_REQUESTS = 100;
    private static final Duration WINDOW = Duration.ofMinutes(1);

    private final StringRedisTemplate redisTemplate;

    public boolean isAllowed(String clientIp) {

        String key = "rate_limit:" + clientIp;

        Long requests = redisTemplate.opsForValue().increment(key);

        if (requests != null && requests == 1) {
            redisTemplate.expire(key, WINDOW);
        }

        return requests != null && requests <= MAX_REQUESTS;
    }
}
