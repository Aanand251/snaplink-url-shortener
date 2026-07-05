package com.anand.url_shortner.controller;

import com.anand.url_shortner.service.ClickTrackingService;
import com.anand.url_shortner.service.RedirectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final RedirectService redirectService;
    private final ClickTrackingService clickTrackingService;

    @GetMapping("/r/{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortCode,
            @RequestHeader(value = "User-Agent", required = false) String userAgent,
            HttpServletRequest request) {

        String originalUrl = redirectService.getOriginalUrl(shortCode);

        String ipAddress = request.getRemoteAddr();

        clickTrackingService.saveClick(
                shortCode,
                userAgent,
                ipAddress
        );

        return ResponseEntity
                .status(302)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }
}