package com.anand.url_shortner.repository.projection;

public interface TopLinkProjection {

    String getShortCode();

    String getOriginalUrl();

    Long getTotalClicks();

}