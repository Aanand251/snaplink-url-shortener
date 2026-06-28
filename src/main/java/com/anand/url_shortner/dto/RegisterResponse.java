package com.anand.url_shortner.dto;

import jakarta.websocket.OnMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {

    private boolean success;


   private String message;
}
