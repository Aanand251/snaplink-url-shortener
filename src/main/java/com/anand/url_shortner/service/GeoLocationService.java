package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.GeoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeoLocationService {

    private final RestTemplate restTemplate;

    public String getCountry(String ipAddress) {
        if(ipAddress == null) {
            return "Unknown";
        }
        if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
            return "LocalHost";
        }
        try{
            String url = "http://ip-api.com/json/" + ipAddress;

            GeoResponse response =
                    restTemplate.
                            getForObject(
                                    url,
                                    GeoResponse.class);
            return response != null ?
                    response.getCountry()
                    : "Unknown";
        }
        catch (Exception e) {
            return "Unknown";
        }
    }
}
