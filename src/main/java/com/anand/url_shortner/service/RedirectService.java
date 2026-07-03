package com.anand.url_shortner.service;

import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.exception.UrlNotFoundException;
import com.anand.url_shortner.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Slf4j
@Service
@RequiredArgsConstructor
public class RedirectService {

    private final UrlRepository urlRepository;
    private final RedisService redisService;
    private static final String URL_CACHE_PREFIX = "url:";

    public String getOriginalUrl(String shortCode) {



        if (shortCode == null || shortCode.isBlank()) {
            return null;
        }

        String cacheKey = URL_CACHE_PREFIX + shortCode;

        // 1. Check Redis
        String cachedUrl = redisService.get(cacheKey);

        if (cachedUrl != null) {
            log.info("Cache HIT for short code: {}", shortCode);
            return cachedUrl;
        }

        // 2. Cache Miss -> Database
        UrlMapping urlMapping = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException(shortCode));

        // 3. Save into Redis
        redisService.save(
                cacheKey,
                urlMapping.getOriginalUrl(),
                Duration.ofHours(24)
        );

        log.info("Cache MISS. Loaded from DB and cached: {}", shortCode);

        return urlMapping.getOriginalUrl();
    }

}
