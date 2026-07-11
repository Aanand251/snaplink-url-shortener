package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.GeoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoLocationService {

    private final RestTemplate restTemplate;
    private final String geoApiUrl;

    public GeoLocationService(
            RestTemplate restTemplate,
            @Value("${geo.api.url}") String geoApiUrl
    ) {
        this.restTemplate = restTemplate;
        this.geoApiUrl = geoApiUrl;
    }

    public String getCountry(String ipAddress) {

        if (ipAddress == null) {
            return "Unknown";
        }

        if (ipAddress.equals("127.0.0.1")
                || ipAddress.equals("0:0:0:0:0:0:0:1")) {

            return "LocalHost";
        }

        try {

            String url = geoApiUrl + ipAddress;

            GeoResponse response =
                    restTemplate.getForObject(
                            url,
                            GeoResponse.class
                    );

            return response != null
                    ? response.getCountry()
                    : "Unknown";

        } catch (Exception e) {

            return "Unknown";
        }
    }
}