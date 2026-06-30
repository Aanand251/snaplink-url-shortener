package com.anand.url_shortner.repository;

import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface UrlRepository
        extends JpaRepository<UrlMapping , Long>{

    Optional<UrlMapping> findByShortCode(String shortCode);

    List<UrlMapping> findByUser(User user);

    Optional<UrlMapping> findByIdAndUser(Long id, User user);


}