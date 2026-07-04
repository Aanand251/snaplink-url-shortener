package com.anand.url_shortner.scheduler;

import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClickCounterSyncScheduler {

    private final RedisService redisService;
    private final UrlRepository urlRepository;

    @Scheduled(fixedRate = 60000)
    public void syncClickCounters() {

        log.info("Starting Click Counter Synchronization...");

        Set<String> keys = redisService.getKeys("click:count:*");

        if (keys == null || keys.isEmpty()) {
            log.info("No click counters found.");
            return;
        }

        for (String key : keys) {

            try {

                String shortCode =
                        key.replace("click:count:", "");

                Long clickCount =
                        redisService.getLong(key);

                int updatedRows =
                        urlRepository.incrementTotalClicks(
                                shortCode,
                                clickCount
                        );

                if (updatedRows == 0) {

                    log.warn(
                            "No UrlMapping found for {}",
                            shortCode
                    );

                    continue;
                }
                redisService.delete(key);

                log.info(
                        "Synced {} clicks for {}",
                        clickCount,
                        shortCode
                );

            } catch (Exception e) {

                log.error(
                        "Failed syncing key {}",
                        key,
                        e
                );

            }

        }

        log.info("Click Counter Synchronization Completed.");

    }

}