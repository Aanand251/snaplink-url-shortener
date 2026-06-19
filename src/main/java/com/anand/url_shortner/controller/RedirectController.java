package com.anand.url_shortner.controller;
import com.anand.url_shortner.repository.ClickRepository;
import com.anand.url_shortner.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedirectController {
    private final UrlService urlService;
    private  final ClickRepository clickRepository;

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void>  redirect  (
            @PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);

        if(originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(302)
                .header(HttpHeaders.LOCATION , originalUrl)
                .build();

    }


}
