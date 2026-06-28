package com.anand.url_shortner.auth;

import com.anand.url_shortner.dto.LoginRequest;
import com.anand.url_shortner.dto.LoginResponse;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword());

    if(!matches){
        throw new RuntimeException("Invalid email or password");
    }
        String token = jwtService.generateToken(user.getEmail());

    return new LoginResponse(
            true,
            "Login Successful",
               token
    );
}
}
