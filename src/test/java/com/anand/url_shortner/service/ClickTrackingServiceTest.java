package com.anand.url_shortner.service;

import com.anand.url_shortner.entity.Clickevent;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.repository.ClickRepository;
import com.anand.url_shortner.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClickTrackingServiceTest {

    @Mock
    private ClickRepository clickRepository;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private GeoLocationService geoLocationService;

    @Mock
    private RedisService redisService;

    @InjectMocks
    private ClickTrackingService clickTrackingService;

    private UrlMapping urlMapping;

    @BeforeEach
    void setUp() {

        urlMapping = new UrlMapping();
        urlMapping.setShortCode("abcd");
        urlMapping.setOriginalUrl("https://google.com");
    }

    @Test
    @DisplayName("Should Save Click Successfully")
    void shouldSaveClick() {

        when(urlRepository.findByShortCode("abcd"))
                .thenReturn(Optional.of(urlMapping));

        when(geoLocationService.getCountry("127.0.0.1"))
                .thenReturn("India");

        clickTrackingService.saveClick(
                "abcd",
                "Chrome Mobile",
                "127.0.0.1"
        );

        verify(redisService)
                .increment(anyString());

        ArgumentCaptor<Clickevent> captor =
                ArgumentCaptor.forClass(Clickevent.class);

        verify(clickRepository)
                .save(captor.capture());

        Clickevent event = captor.getValue();

        assertEquals("Chrome", event.getBrowser());
        assertEquals("Mobile", event.getDevice());
        assertEquals("India", event.getCountry());

        assertNotNull(event.getClickedAt());
        assertEquals(urlMapping, event.getUrlMapping());
    }

    @Test
    @DisplayName("Should Not Save Click When URL Not Found")
    void shouldNotSaveClickWhenUrlNotFound() {

        when(urlRepository.findByShortCode("abcd"))
                .thenReturn(Optional.empty());

        clickTrackingService.saveClick(
                "abcd",
                "Chrome",
                "127.0.0.1"
        );

        verify(clickRepository, never())
                .save(any());
    }

    @Test
    @DisplayName("Should Return Click Count")
    void getClickCount_shouldReturnCount() {

        when(clickRepository.countByUrlMapping_ShortCode("abcd"))
                .thenReturn(100L);

        long count =
                clickTrackingService.getClickCount("abcd");

        assertEquals(100, count);
    }

    @Test
    @DisplayName("Detect Browser Should Return Chrome")
    void detectBrowser_shouldReturnChrome() {

        assertEquals(
                "Chrome",
                clickTrackingService.detectBrowser(
                        "Chrome Mobile"
                )
        );
    }

    @Test
    @DisplayName("Detect Browser Should Return Firefox")
    void detectBrowser_shouldReturnFirefox() {

        assertEquals(
                "Firefox",
                clickTrackingService.detectBrowser(
                        "Firefox"
                )
        );
    }

    @Test
    @DisplayName("Detect Browser Should Return Unknown")
    void detectBrowser_shouldReturnUnknown() {

        assertEquals(
                "Unknown",
                clickTrackingService.detectBrowser(
                        "Random Browser"
                )
        );
    }

    @Test
    @DisplayName("Detect Browser Should Return Unknown For Null")
    void detectBrowser_shouldReturnUnknownWhenNull() {

        assertEquals(
                "Unknown",
                clickTrackingService.detectBrowser(null)
        );
    }

    @Test
    @DisplayName("Detect Device Should Return Mobile")
    void detectDevice_shouldReturnMobile() {

        assertEquals(
                "Mobile",
                clickTrackingService.detectDevice(
                        "Chrome Mobile"
                )
        );
    }

    @Test
    @DisplayName("Detect Device Should Return Desktop")
    void detectDevice_shouldReturnDesktop() {

        assertEquals(
                "Desktop",
                clickTrackingService.detectDevice(
                        "Chrome Windows"
                )
        );
    }

    @Test
    @DisplayName("Detect Device Should Return Unknown For Null")
    void detectDevice_shouldReturnUnknown() {

        assertEquals(
                "Unknown",
                clickTrackingService.detectDevice(null)
        );
    }

    @Test
    @DisplayName("Detect Country Should Return Country")
    void detectCountry_shouldReturnCountry() {

        when(geoLocationService.getCountry("127.0.0.1"))
                .thenReturn("India");

        String country =
                clickTrackingService.detectCountry("127.0.0.1");

        assertEquals("India", country);

        verify(geoLocationService)
                .getCountry("127.0.0.1");
    }

}