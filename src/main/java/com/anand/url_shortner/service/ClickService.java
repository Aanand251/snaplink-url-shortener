package com.anand.url_shortner.service;

import com.anand.url_shortner.entity.Clickevent;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.repository.ClickRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.anand.url_shortner.repository.UrlRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class  ClickService {

    private final ClickRepository clickRepository;
    private final UrlRepository urlRepository;


    public void saveClick(String shortCode) {
        Optional<UrlMapping> result =
                urlRepository.findByShortCode(shortCode);
if (result.isPresent()) {
    UrlMapping urlMapping = result.get();
    Clickevent clickevent = new Clickevent();
    clickevent.setUrlMapping(urlMapping);
    clickevent.setClickedAt(new Date());
    clickRepository.save(clickevent);
    log.info("Click recorded for short code: {}", shortCode);

}


    }

    public  long getClickCount(String shortCode) {
        return clickRepository
                .countByUrlMapping_ShortCode(shortCode);
    }


}
