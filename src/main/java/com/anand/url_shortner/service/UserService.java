package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.RegisterRequest;
import com.anand.url_shortner.dto.RegisterResponse;
import com.anand.url_shortner.entity.Role;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.exception.BadRequestException;
import com.anand.url_shortner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return new RegisterResponse(
                true,
                "User registered successfully"
        );
    }
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        if (user.isSuspended()) {

            if (user.getSuspensionType() != null
                    && user.getSuspensionType().name().equals("HARD")) {

                throw new BadRequestException(
                        "Your account has been permanently suspended."
                );
            }

            if (user.getSuspendedUntil() == null
                    || user.getSuspendedUntil().isAfter(LocalDateTime.now())) {

                throw new BadRequestException(
                        "Your account is temporarily suspended until "
                                + user.getSuspendedUntil()
                );
            }

            user.setSuspended(false);
            user.setSuspensionType(null);
            user.setSuspendedUntil(null);
            user.setSuspendedAt(null);
            user.setSuspendedBy(null);
        }

        return user;
    }



}
