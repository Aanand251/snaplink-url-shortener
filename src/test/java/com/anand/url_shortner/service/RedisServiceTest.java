package com.anand.url_shortner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    private RedisService redisService;

    @BeforeEach
    void setUp() {

        redisService = new RedisService(redisTemplate);
    }

    @Test
    @DisplayName("Should Save Value With TTL")
    void save_shouldSaveValueWithTtl() {

        String key = "url:github";
        String value = "https://github.com";
        Duration ttl = Duration.ofMinutes(10);

        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        redisService.save(key, value, ttl);

        verify(valueOperations)
                .set(key, value, ttl);
    }

    @Test
    @DisplayName("Should Get Value")
    void get_shouldReturnValue() {

        String key = "url:github";

        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        when(valueOperations.get(key))
                .thenReturn("https://github.com");

        String result = redisService.get(key);

        assertEquals(
                "https://github.com",
                result
        );

        verify(valueOperations).get(key);
    }

    @Test
    @DisplayName("Typed Get Should Return Value")
    void typedGet_shouldReturnValue() {

        String key = "url:github";

        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        when(valueOperations.get(key))
                .thenReturn("github");

        String result =
                redisService.get(key, String.class);

        assertEquals("github", result);

        verify(valueOperations).get(key);
    }

    @Test
    @DisplayName("Typed Get Should Return Null When Value Does Not Exist")
    void typedGet_shouldReturnNullWhenValueDoesNotExist() {

        String key = "missing";

        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        when(valueOperations.get(key))
                .thenReturn(null);

        String result =
                redisService.get(key, String.class);

        assertNull(result);

        verify(valueOperations).get(key);
    }

    @Test
    @DisplayName("Should Delete Key")
    void delete_shouldDeleteKey() {

        String key = "url:github";

        redisService.delete(key);

        verify(redisTemplate).delete(key);
    }

    @Test
    @DisplayName("Exists Should Return True When Key Exists")
    void exists_shouldReturnTrueWhenKeyExists() {

        String key = "url:github";

        when(redisTemplate.hasKey(key))
                .thenReturn(true);

        boolean result = redisService.exists(key);

        assertTrue(result);

        verify(redisTemplate).hasKey(key);
    }

    @Test
    @DisplayName("Exists Should Return False When Key Does Not Exist")
    void exists_shouldReturnFalseWhenKeyDoesNotExist() {

        String key = "missing";

        when(redisTemplate.hasKey(key))
                .thenReturn(false);

        boolean result = redisService.exists(key);

        assertFalse(result);

        verify(redisTemplate).hasKey(key);
    }

    @Test
    @DisplayName("Exists Should Return False When Redis Returns Null")
    void exists_shouldReturnFalseWhenRedisReturnsNull() {

        String key = "github";

        when(redisTemplate.hasKey(key))
                .thenReturn(null);

        boolean result = redisService.exists(key);

        assertFalse(result);

        verify(redisTemplate).hasKey(key);
    }

    @Test
    @DisplayName("Should Increment Value")
    void increment_shouldReturnIncrementedValue() {

        String key = "click:count:github";

        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        when(valueOperations.increment(key))
                .thenReturn(11L);

        Long result = redisService.increment(key);

        assertEquals(11L, result);

        verify(valueOperations).increment(key);
    }

    @Test
    @DisplayName("Should Return Keys Matching Pattern")
    void getKeys_shouldReturnMatchingKeys() {

        String pattern = "click:count:*";

        Set<String> keys = Set.of(
                "click:count:github",
                "click:count:google"
        );

        when(redisTemplate.keys(pattern))
                .thenReturn(keys);

        Set<String> result =
                redisService.getKeys(pattern);

        assertEquals(keys, result);

        verify(redisTemplate).keys(pattern);
    }

    @Test
    @DisplayName("Get Long Should Return Parsed Long Value")
    void getLong_shouldReturnParsedLongValue() {

        String key = "click:count:github";

        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        when(valueOperations.get(key))
                .thenReturn("25");

        Long result = redisService.getLong(key);

        assertEquals(25L, result);

        verify(valueOperations).get(key);
    }

    @Test
    @DisplayName("Get Long Should Return Zero When Value Does Not Exist")
    void getLong_shouldReturnZeroWhenValueDoesNotExist() {

        String key = "missing";

        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        when(valueOperations.get(key))
                .thenReturn(null);

        Long result = redisService.getLong(key);

        assertEquals(0L, result);

        verify(valueOperations).get(key);
    }

    @Test
    @DisplayName("Should Delete Multiple Keys")
    void deleteKeys_shouldDeleteMultipleKeys() {

        Set<String> keys = Set.of(
                "click:count:github",
                "click:count:google"
        );

        redisService.deleteKeys(keys);

        verify(redisTemplate).delete(keys);
    }

    @Test
    @DisplayName("Should Not Delete Keys When Set Is Null")
    void deleteKeys_shouldNotDeleteWhenKeysAreNull() {

        redisService.deleteKeys(null);

        verify(redisTemplate, never())
                .delete(org.mockito.ArgumentMatchers.<Set<String>>any());
    }

    @Test
    @DisplayName("Should Not Delete Keys When Set Is Empty")
    void deleteKeys_shouldNotDeleteWhenKeysAreEmpty() {

        redisService.deleteKeys(Set.of());

        verify(redisTemplate, never())
                .delete(org.mockito.ArgumentMatchers.<Set<String>>any());
    }
}