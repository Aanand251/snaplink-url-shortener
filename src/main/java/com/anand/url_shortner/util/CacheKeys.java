package com.anand.url_shortner.util;

public final  class CacheKeys {
    private CacheKeys() {

    }

    public static String url(String shortCode){
        return "url:"+shortCode;
    }

    public static String clickCount(String shortCode){
        return "click:count:"+shortCode;
    }

    public static String clickCounterPattern() {
        return "click:count:*";
    }

    public static String extractShortCode(String key) {
        return key.replace("click:count:", "");
    }
}
