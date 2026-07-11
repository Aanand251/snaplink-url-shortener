package com.anand.url_shortner.controller;

import com.anand.url_shortner.dto.CreateUrlRequest;
import com.anand.url_shortner.dto.DashboardResponse;
import com.anand.url_shortner.dto.UpdateUrlRequest;
import com.anand.url_shortner.dto.UrlResponse;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.service.DashboardService;
import com.anand.url_shortner.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @Operation(
            summary = "Get All URLs",
            description = "Returns all stored URL mappings"
    )
    @GetMapping
    public ResponseEntity<List<UrlMapping>> getAllUrls() {

        return ResponseEntity.ok(
                urlService.getAllUrls()
        );
    }

    @Operation(
            summary = "Create Short URL",
            description = "Creates a short URL from a long URL"
    )
    @PostMapping("/shorten")
    public ResponseEntity<String> createShortUrl(
            @Valid @RequestBody CreateUrlRequest request
    ) {

        String shortCode =
                urlService.createShortUrl(request);

        return ResponseEntity.ok(shortCode);
    }

    @GetMapping("/dashboard/{shortCode}")
    public ResponseEntity<DashboardResponse> getDashboard(
            @PathVariable String shortCode
    ) {

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
    public ResponseEntity<String> deleteUrl(
            @PathVariable Long id
    ) {

        urlService.deleteUrl(id);

        return ResponseEntity.ok(
                "URL deleted successfully"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUrl(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUrlRequest request
    ) {

        urlService.updateUrl(id, request);

        return ResponseEntity.ok(
                "URL updated successfully"
        );
    }
}