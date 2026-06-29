package com.anand.url_shortner.controller;

import com.anand.url_shortner.auth.AuthenticationService;
import com.anand.url_shortner.dto.LoginRequest;
import com.anand.url_shortner.dto.LoginResponse;
import com.anand.url_shortner.dto.RegisterRequest;
import com.anand.url_shortner.dto.RegisterResponse;
import com.anand.url_shortner.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private   final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(
          @Valid @RequestBody RegisterRequest  request) {

      RegisterResponse response = userService.register(request);

      return  ResponseEntity
              .status(HttpStatus.CREATED)
              .body(response);
    }
    @PostMapping("/login")
      public ResponseEntity<LoginResponse> login(
                @Valid  @RequestBody  LoginRequest request
                ) {
          LoginResponse response = authenticationService.login(request);
          return ResponseEntity .ok(response);
        }

    @GetMapping("/me")
    public String me() {
        return "Authenticated Successfully";
    }


}
