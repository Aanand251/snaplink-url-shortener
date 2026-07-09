package com.anand.url_shortner.service;

import com.anand.url_shortner.entity.Role;
import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UrlRepository urlRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UrlMapping> getAllUrls() {
        return urlRepository.findAll();
    }

    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }

    public void makeAdmin(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setRole(Role.ADMIN);

        userRepository.save(user);
    }

    public void makeUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setRole(Role.USER);

        userRepository.save(user);
    }

}