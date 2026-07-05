package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.CreateUrlRequest;
import com.anand.url_shortner.dto.UpdateUrlRequest;
import com.anand.url_shortner.dto.UrlResponse;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.exception.UrlNotFoundException;
import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.util.CacheKeys;
import lombok.RequiredArgsConstructor;
import com.anand.url_shortner.exception.ResourceAccessDeniedException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;




@Slf4j
@Service
@RequiredArgsConstructor
public class  UrlService {

   // repo injection
   private final UrlRepository urlRepository;
    private final UserService userService;
    private final RedisService redisService;



// creating short url;
public String createShortUrl(CreateUrlRequest request) {

    if (request == null ||
            request.getOriginalUrl() == null ||
            request.getOriginalUrl().isBlank()) {

        throw new IllegalArgumentException("Original URL cannot be empty");
    }

    String shortCode = generateShortCode();

    while (urlRepository.findByShortCode(shortCode).isPresent()) {
        shortCode = generateShortCode();
    }

    User currentUser = userService.getCurrentUser();

    UrlMapping urlMapping = new UrlMapping();
    urlMapping.setOriginalUrl(request.getOriginalUrl());
    urlMapping.setShortCode(shortCode);
    urlMapping.setCreatedAt(LocalDateTime.now());
    urlMapping.setExpiresAt(request.getExpiresAt());
    urlMapping.setUser(currentUser);

    urlRepository.save(urlMapping);

    log.info("Short URL created successfully. ShortCode: {}", shortCode);

    return shortCode;
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

//GETURLLIST
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

//DELETE
public void deleteUrl(Long id) {

    User currentUser = userService.getCurrentUser();

    UrlMapping urlMapping = urlRepository
            .findByIdAndUser(id, currentUser)
            .orElseThrow(ResourceAccessDeniedException::new);

    urlRepository.delete(urlMapping);
    redisService.delete(CacheKeys.url(urlMapping.getShortCode()));
    log.info("URL deleted successfully. ID: {}", id);
}

    //update
    public void updateUrl(Long id, UpdateUrlRequest request) {

        User currentUser = userService.getCurrentUser();

        UrlMapping urlMapping = urlRepository
                .findByIdAndUser(id, currentUser)
                .orElseThrow(ResourceAccessDeniedException::new);

        urlMapping.setOriginalUrl(request.getOriginalUrl());

        urlRepository.save(urlMapping);
        redisService.delete(CacheKeys.url(urlMapping.getShortCode()));

        log.info("URL updated successfully. ID: {}", id);
    }

}
