package com.anand.url_shortner.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}