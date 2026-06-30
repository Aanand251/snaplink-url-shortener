package com.anand.url_shortner.controller;

import com.anand.url_shortner.dto.AnalyticsResponse;
import com.anand.url_shortner.dto.CreateUrlRequest;

import com.anand.url_shortner.dto.DashboardResponse;
import com.anand.url_shortner.dto.UrlResponse;
import com.anand.url_shortner.entity.UrlMapping;

import com.anand.url_shortner.repository.ClickRepository;
import com.anand.url_shortner.service.ClickService;
import com.anand.url_shortner.service.DashboardService;
import com.anand.url_shortner.service.UrlService;
import com.anand.url_shortner.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/url")
@RequiredArgsConstructor
@Tag(
        name = "URL Controller",
        description = "URL Shortening APIs"
)
public class UrlController {

    private final DashboardService dashboardService;

 private final UrlService urlService;
 private final ClickService clickService;
    private final ClickRepository clickRepository;


    @Operation(
            summary = "Get All URLs",
            description = "Returns all stored URL mappings"
    )
@GetMapping
 public ResponseEntity<List<UrlMapping>> getAllUrls() {
     return  ResponseEntity.ok(urlService
             .getAllUrls());
 }


    @Operation(
            summary = "Create Short URL",
            description = "Creates a short URL from a long URL"
    )
 @PostMapping("/shorten")
 public ResponseEntity<String>  createUrl (@Valid @RequestBody CreateUrlRequest request) {
    String shortcode = urlService.createShortUrl(request.getOriginalUrl());
  return  ResponseEntity
          .status(HttpStatus.CREATED)
          .body(shortcode);
 }


    @Operation(
            summary = "Get Analytics",
            description = "Returns click count for a short URL"
    )
    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<AnalyticsResponse> getClickCount(
            @PathVariable String shortCode) {
        long clicks = clickService.getClickCount(shortCode);

        String topBrowser = clickRepository.findTopBrowser(shortCode);
        String topDevice = clickRepository.findTopDevice(shortCode);
        AnalyticsResponse response =
                new AnalyticsResponse(shortCode, clicks, topBrowser, topDevice);
        return ResponseEntity.ok(response);

    }
        @GetMapping("/dashboard/{shortCode}")
        public ResponseEntity<DashboardResponse> getDashboard(
                @PathVariable String shortCode) {

            return ResponseEntity.ok(
                    dashboardService.getDashboard(shortCode)
            );
        }

    @GetMapping("/my-urls")
    public ResponseEntity<List<UrlResponse>> getMyUrls() {

        return ResponseEntity.ok(
                urlService.getMyUrls()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUrl(@PathVariable Long id) {
        System.out.println("===== DELETE CONTROLLER HIT =====");
        urlService.deleteUrl(id);

        return ResponseEntity.ok("URL deleted successfully");
    }
    }

