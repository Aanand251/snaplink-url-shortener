package com.anand.url_shortner.service;

import com.anand.url_shortner.entity.Clickevent;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.repository.ClickRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.anand.url_shortner.repository.UrlRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClickService {

    private final ClickRepository clickRepository;
    private final UrlRepository urlRepository;
    private final GeoLocationService geoLocationService;


    public void saveClick(String shortCode, String browser,
                          String device , String country) {
        Optional<UrlMapping> result =
                urlRepository.findByShortCode(shortCode);
        if (result.isPresent()) {
            UrlMapping urlMapping = result.get();
            Clickevent clickevent = new Clickevent();
            clickevent.setUrlMapping(urlMapping);
            clickevent.setBrowser(browser);
            clickevent.setDevice(device);
            clickevent.setCountry(country);
            clickevent.setClickedAt(LocalDateTime.now());

            clickRepository.save(clickevent);
            log.info("Click recorded for short code: {}", shortCode);

        }


    }

    public long getClickCount(String shortCode) {
        return clickRepository
                .countByUrlMapping_ShortCode(shortCode);
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
