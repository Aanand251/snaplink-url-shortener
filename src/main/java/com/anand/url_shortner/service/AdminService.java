package com.anand.url_shortner.service;

import com.anand.url_shortner.dto.AdminUrlResponse;
import com.anand.url_shortner.dto.AdminUserResponse;
import com.anand.url_shortner.entity.Role;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UrlRepository urlRepository;

    @Transactional(readOnly = true)
    public List<AdminUserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AdminUrlResponse> getAllUrls() {

        return urlRepository.findAll()
                .stream()
                .map(this::mapToUrlResponse)
                .toList();
    }

    @Transactional
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }

    @Transactional
    public void makeAdmin(Long id) {

        User user = findUserById(id);

        user.setRole(Role.ADMIN);
    }

    @Transactional
    public void makeUser(Long id) {

        User user = findUserById(id);

        user.setRole(Role.USER);
    }

    private User findUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }

    private AdminUserResponse mapToUserResponse(User user) {

        return AdminUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    private AdminUrlResponse mapToUrlResponse(UrlMapping urlMapping) {

        User owner = urlMapping.getUser();

        return AdminUrlResponse.builder()
                .id(urlMapping.getId())
                .originalUrl(urlMapping.getOriginalUrl())
                .shortCode(urlMapping.getShortCode())
                .totalClicks(urlMapping.getTotalClicks())
                .createdAt(urlMapping.getCreatedAt())
                .userId(owner != null ? owner.getId() : null)
                .userEmail(owner != null ? owner.getEmail() : null)
                .build();
    }
}