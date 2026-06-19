package com.anand.url_shortner.service;

import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.exception.UrlNotFoundException;
import com.anand.url_shortner.repository.UrlRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

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


// creating short url;
    public String createShortUrl(String originalUrl) {
        String shortcode = generateShortCode();
        if (originalUrl == null || originalUrl.isEmpty()) {
            throw new IllegalArgumentException("Original URL cannot be empty");
      }
      while(urlRepository.findByShortCode(shortcode).isPresent()) {
          shortcode = generateShortCode();
      }
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortCode(shortcode);
        urlMapping.setCreatedAt(new Date());

        urlRepository.save(urlMapping);
        log.info("Short URL created: {}", shortcode);
        return shortcode;
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

}
