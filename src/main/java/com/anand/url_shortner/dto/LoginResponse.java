package com.anand.url_shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public  class LoginResponse {

    private boolean success;

    private String message;

    private String token;
}
