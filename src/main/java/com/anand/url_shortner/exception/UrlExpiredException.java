package com.anand.url_shortner.exception;

public class UrlExpiredException extends RuntimeException {

    public UrlExpiredException(String shortCode) {
        super("URL has expired: " + shortCode);
    }
}