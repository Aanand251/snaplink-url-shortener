package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.DashboardResponse;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.repository.ClickRepository;
import com.anand.url_shortner.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
 public class DashboardService {

    private  final UrlRepository urlRepository;
    private final ClickRepository clickRepository;
    private final ClickService clickService;

    public DashboardResponse getDashboard(String shortCode) {

        UrlMapping url = urlRepository
                .findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("No url found for shortCode: " + shortCode));

        return new DashboardResponse(
                url.getOriginalUrl(),
                shortCode,
                clickService.getClickCount(shortCode),
                clickRepository.findTopBrowser(shortCode),
                clickRepository.findTopDevice(shortCode),
                clickRepository.findTopCountry(shortCode),
                url.getCreatedAt(),
                clickRepository.findLastClickedAt(shortCode)
        );


    }
}
