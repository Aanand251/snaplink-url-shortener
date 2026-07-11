package com.anand.url_shortner.scheduler;

import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.service.RedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClickCounterSyncSchedulerTest {

    private static final String CLICK_COUNT_PATTERN = "click:count:*";

    @Mock
    private RedisService redisService;

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private ClickCounterSyncScheduler scheduler;

    @Test
    @DisplayName("Should Return When Redis Keys Are Null")
    void syncClickCounters_shouldReturnWhenKeysAreNull() {

        when(redisService.getKeys(CLICK_COUNT_PATTERN))
                .thenReturn(null);

        scheduler.syncClickCounters();

        verify(redisService).getKeys(CLICK_COUNT_PATTERN);
        verifyNoInteractions(urlRepository);
    }

    @Test
    @DisplayName("Should Return When Redis Keys Are Empty")
    void syncClickCounters_shouldReturnWhenKeysAreEmpty() {

        when(redisService.getKeys(CLICK_COUNT_PATTERN))
                .thenReturn(Set.of());

        scheduler.syncClickCounters();

        verify(redisService).getKeys(CLICK_COUNT_PATTERN);
        verifyNoInteractions(urlRepository);
    }

    @Test
    @DisplayName("Should Sync Click Counter And Delete Redis Key")
    void syncClickCounters_shouldSyncAndDeleteRedisKey() {

        String key = "click:count:github";

        when(redisService.getKeys(CLICK_COUNT_PATTERN))
                .thenReturn(Set.of(key));

        when(redisService.getLong(key))
                .thenReturn(10L);

        when(urlRepository.incrementTotalClicks("github", 10L))
                .thenReturn(1);

        scheduler.syncClickCounters();

        verify(redisService).getKeys(CLICK_COUNT_PATTERN);
        verify(redisService).getLong(key);

        verify(urlRepository)
                .incrementTotalClicks("github", 10L);

        verify(redisService).delete(key);
    }

    @Test
    @DisplayName("Should Not Delete Redis Key When URL Is Not Found")
    void syncClickCounters_shouldNotDeleteKeyWhenUrlNotFound() {

        String key = "click:count:missing";

        when(redisService.getKeys(CLICK_COUNT_PATTERN))
                .thenReturn(Set.of(key));

        when(redisService.getLong(key))
                .thenReturn(5L);

        when(urlRepository.incrementTotalClicks("missing", 5L))
                .thenReturn(0);

        scheduler.syncClickCounters();

        verify(urlRepository)
                .incrementTotalClicks("missing", 5L);

        verify(redisService, never())
                .delete(key);
    }

    @Test
    @DisplayName("Should Catch Exception And Keep Redis Key")
    void syncClickCounters_shouldCatchExceptionAndKeepRedisKey() {

        String key = "click:count:github";

        when(redisService.getKeys(CLICK_COUNT_PATTERN))
                .thenReturn(Set.of(key));

        when(redisService.getLong(key))
                .thenThrow(new RuntimeException("Redis failure"));

        scheduler.syncClickCounters();

        verify(redisService).getLong(key);

        verifyNoInteractions(urlRepository);

        verify(redisService, never())
                .delete(key);
    }

    @Test
    @DisplayName("Should Continue Processing Remaining Keys After Failure")
    void syncClickCounters_shouldContinueAfterOneKeyFails() {

        String failedKey = "click:count:failed";
        String validKey = "click:count:google";

        Set<String> keys = new LinkedHashSet<>();
        keys.add(failedKey);
        keys.add(validKey);

        when(redisService.getKeys(CLICK_COUNT_PATTERN))
                .thenReturn(keys);

        when(redisService.getLong(failedKey))
                .thenThrow(new RuntimeException("Redis failure"));

        when(redisService.getLong(validKey))
                .thenReturn(7L);

        when(urlRepository.incrementTotalClicks("google", 7L))
                .thenReturn(1);

        scheduler.syncClickCounters();

        verify(redisService).getLong(failedKey);
        verify(redisService).getLong(validKey);

        verify(urlRepository)
                .incrementTotalClicks("google", 7L);

        verify(redisService, never())
                .delete(failedKey);

        verify(redisService)
                .delete(validKey);
    }
}