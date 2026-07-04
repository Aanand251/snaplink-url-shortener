package com.anand.url_shortner.service;

import com.anand.url_shortner.entity.Clickevent;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.repository.ClickRepository;
import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.util.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClickTrackingService {

    private final ClickRepository clickRepository;
    private final UrlRepository urlRepository;
    private final GeoLocationService geoLocationService;
    private final RedisService redisService;

    @Async
    public void saveClick(
            String shortCode,
            String userAgent,
            String ipAddress) {

        String browser = detectBrowser(userAgent);
        String device = detectDevice(userAgent);
        String country = detectCountry(ipAddress);
        redisService.increment(
                CacheKeys.clickCount(shortCode)
        );

        UrlMapping urlMapping = urlRepository.findByShortCode(shortCode)
                .orElse(null);

        if (urlMapping == null) {
            log.warn("URL not found for short code: {}", shortCode);
            return;
        }

        Clickevent clickEvent = new Clickevent();
        clickEvent.setUrlMapping(urlMapping);
        clickEvent.setBrowser(browser);
        clickEvent.setDevice(device);
        clickEvent.setCountry(country);
        clickEvent.setClickedAt(LocalDateTime.now());

        clickRepository.save(clickEvent);

        log.info("Click recorded for short code: {}", shortCode);
    }

    public long getClickCount(String shortCode) {
        return clickRepository.countByUrlMapping_ShortCode(shortCode);
    }

    public String detectBrowser(String userAgent) {

        if (userAgent == null) {
            return "Unknown";
        }

        if (userAgent.contains("Firefox")) {
            return "Firefox";
        }

        if (userAgent.contains("Edg")) {
            return "Edge";
        }

        if (userAgent.contains("Chrome")) {
            return "Chrome";
        }

        if (userAgent.contains("Safari")) {
            return "Safari";
        }

        if (userAgent.contains("Opera")) {
            return "Opera";
        }

        return "Unknown";
    }

    public String detectDevice(String userAgent) {

        if (userAgent == null) {
            return "Unknown";
        }

        if (userAgent.contains("Mobile")) {
            return "Mobile";
        }

        return "Desktop";
    }

    public String detectCountry(String ipAddress) {
        return geoLocationService.getCountry(ipAddress);
    }
}