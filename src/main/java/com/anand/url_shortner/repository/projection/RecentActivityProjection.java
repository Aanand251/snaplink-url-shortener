package com.anand.url_shortner.repository.projection;

import java.time.LocalDateTime;

public interface RecentActivityProjection {

    String getBrowser();

    String getDevice();

    String getCountry();

    LocalDateTime getClickedAt();

}