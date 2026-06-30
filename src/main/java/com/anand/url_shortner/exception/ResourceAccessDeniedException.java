package com.anand.url_shortner.exception;

public class ResourceAccessDeniedException extends RuntimeException {

    public ResourceAccessDeniedException() {
        super("URL not found or access denied");
    }
}