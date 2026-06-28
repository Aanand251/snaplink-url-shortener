package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.RegisterRequest;
import com.anand.url_shortner.dto.RegisterResponse;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw  new RuntimeException("Email already in use");
        }

        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return new RegisterResponse(
                true,
                "User registered successfully"
        );
    }
}
