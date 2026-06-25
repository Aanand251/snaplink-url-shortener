package com.anand.url_shortner.controller;
import com.anand.url_shortner.repository.ClickRepository;
import com.anand.url_shortner.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.anand.url_shortner.service.ClickService;
@Slf4j
@RestController
@RequiredArgsConstructor
public class RedirectController {
    private final UrlService urlService;
    private  final ClickRepository clickRepository;
    private final ClickService clickService;

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void>  redirect  (
            @PathVariable String shortCode,
            @RequestHeader(value = "User-Agent" ,
                 required = false   )
            String userAgent,
            jakarta.servlet.http.HttpServletRequest request) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        String browser = clickService.detectBrowser(userAgent);

        String device = clickService.detectDevice(userAgent);
        String ipAddress = request.getRemoteAddr();
        String country = clickService.detectCountry(ipAddress);

        if(originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        clickService.saveClick(
                shortCode,
                browser,
                device,
                country
        );
        log.info("Browser: {}, Device: {}", browser, device);
        return ResponseEntity
                .status(302)
                .header(HttpHeaders.LOCATION , originalUrl)
                .build();


    }


}
