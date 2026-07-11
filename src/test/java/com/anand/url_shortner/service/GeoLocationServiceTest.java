package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.GeoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeoLocationServiceTest {

    private static final String GEO_API_URL =
            "https://geo.test/json/";

    @Mock
    private RestTemplate restTemplate;

    private GeoLocationService geoLocationService;

    @BeforeEach
    void setUp() {

        geoLocationService =
                new GeoLocationService(
                        restTemplate,
                        GEO_API_URL
                );
    }

    @Test
    @DisplayName("Should Return Unknown When IP Address Is Null")
    void getCountry_shouldReturnUnknownWhenIpIsNull() {

        String result =
                geoLocationService.getCountry(null);

        assertEquals("Unknown", result);

        verify(
                restTemplate,
                never()
        ).getForObject(
                anyString(),
                eq(GeoResponse.class)
        );
    }

    @Test
    @DisplayName("Should Return LocalHost For IPv4 Localhost")
    void getCountry_shouldReturnLocalHostForIpv4() {

        String result =
                geoLocationService.getCountry(
                        "127.0.0.1"
                );

        assertEquals("LocalHost", result);

        verify(
                restTemplate,
                never()
        ).getForObject(
                anyString(),
                eq(GeoResponse.class)
        );
    }

    @Test
    @DisplayName("Should Return LocalHost For IPv6 Localhost")
    void getCountry_shouldReturnLocalHostForIpv6() {

        String result =
                geoLocationService.getCountry(
                        "0:0:0:0:0:0:0:1"
                );

        assertEquals("LocalHost", result);

        verify(
                restTemplate,
                never()
        ).getForObject(
                anyString(),
                eq(GeoResponse.class)
        );
    }

    @Test
    @DisplayName("Should Return Country For Valid Public IP")
    void getCountry_shouldReturnCountryForValidIp() {

        String ipAddress = "8.8.8.8";

        String expectedUrl =
                GEO_API_URL + ipAddress;

        GeoResponse geoResponse =
                new GeoResponse();

        geoResponse.setCountry("United States");

        when(
                restTemplate.getForObject(
                        expectedUrl,
                        GeoResponse.class
                )
        ).thenReturn(geoResponse);

        String result =
                geoLocationService.getCountry(
                        ipAddress
                );

        assertEquals(
                "United States",
                result
        );

        verify(restTemplate)
                .getForObject(
                        expectedUrl,
                        GeoResponse.class
                );
    }

    @Test
    @DisplayName("Should Return Unknown When Geo API Returns Null")
    void getCountry_shouldReturnUnknownWhenResponseIsNull() {

        String ipAddress = "8.8.8.8";

        String expectedUrl =
                GEO_API_URL + ipAddress;

        when(
                restTemplate.getForObject(
                        expectedUrl,
                        GeoResponse.class
                )
        ).thenReturn(null);

        String result =
                geoLocationService.getCountry(
                        ipAddress
                );

        assertEquals("Unknown", result);

        verify(restTemplate)
                .getForObject(
                        expectedUrl,
                        GeoResponse.class
                );
    }

    @Test
    @DisplayName("Should Return Unknown When Geo API Throws Exception")
    void getCountry_shouldReturnUnknownWhenApiFails() {

        String ipAddress = "8.8.8.8";

        String expectedUrl =
                GEO_API_URL + ipAddress;

        when(
                restTemplate.getForObject(
                        expectedUrl,
                        GeoResponse.class
                )
        ).thenThrow(
                new RuntimeException(
                        "Geo API failure"
                )
        );

        String result =
                geoLocationService.getCountry(
                        ipAddress
                );

        assertEquals("Unknown", result);

        verify(restTemplate)
                .getForObject(
                        expectedUrl,
                        GeoResponse.class
                );
    }
}