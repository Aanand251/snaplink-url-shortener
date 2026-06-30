package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.UrlResponse;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.exception.UrlNotFoundException;
import com.anand.url_shortner.repository.UrlRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;




@Slf4j
@Service
@RequiredArgsConstructor
public class  UrlService {

   // repo injection
   private final UrlRepository urlRepository;
    private final ClickService clickService;
    private final UserService userService;


// creating short url;
public String createShortUrl(String originalUrl) {

    if (originalUrl == null || originalUrl.isBlank()) {
        throw new IllegalArgumentException("Original URL cannot be empty");
    }

    String shortCode = generateShortCode();

    while (urlRepository.findByShortCode(shortCode).isPresent()) {
        shortCode = generateShortCode();
    }

    User currentUser = userService.getCurrentUser();

    UrlMapping urlMapping = new UrlMapping();
    urlMapping.setOriginalUrl(originalUrl);
    urlMapping.setShortCode(shortCode);
    urlMapping.setCreatedAt(LocalDateTime.now());
    urlMapping.setUser(currentUser);

    urlRepository.save(urlMapping);

    log.info("Short URL created: {}", shortCode);

    return shortCode;
}

    public String getOriginalUrl(String shortCode) {
     if(shortCode == null || shortCode.isEmpty()) {
         return null;
     }
    Optional<UrlMapping> result = urlRepository.findByShortCode(shortCode);
     if(result.isPresent()) {
         log.info("Redirecting short code: {}", shortCode);
//         clickService.saveClick(shortCode);

         return result.get().getOriginalUrl();

     }
        log.error("Short code not found: {}", shortCode);
        throw new UrlNotFoundException(shortCode);

    }


// generate 4 random characters
    private String generateShortCode() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortcode = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            int index = rand.nextInt(characters.length());
            shortcode.append(characters.charAt(index));

        }
        return shortcode.toString();
    }
public List<UrlMapping> getAllUrls() {
    log.info("Fetching all URLs");
        return urlRepository.findAll();
}
    public List<UrlResponse> getMyUrls() {
        User currentUser = userService.getCurrentUser();
        return urlRepository.findByUser(currentUser)
                .stream()
                .map(url -> UrlResponse.builder()
                        .id(url.getId())
                        .originalUrl(url.getOriginalUrl())
                        .shortCode(url.getShortCode())
                        .createdAt(url.getCreatedAt())
                        .build())
                .toList();
    }

    public void deleteUrl(Long id) {
        System.out.println("===== DELETE SERVICE HIT =====");
    User currentUser = userService.getCurrentUser();
        System.out.println("Current User = " + currentUser.getEmail());
    UrlMapping urlMapping = urlRepository
            .findByIdAndUser(id, currentUser)
        .orElseThrow(() ->
                new RuntimeException( "URl not found or access denied "));

        System.out.println("Owner = " + urlMapping.getUser().getEmail());
    urlRepository.delete(urlMapping);
    }

}
