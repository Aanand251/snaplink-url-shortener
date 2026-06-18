package com.anand.url_shortner.repository;

import com.anand.url_shortner.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface UrlRepository
        extends JpaRepository<UrlMapping , Long>{

    Optional<UrlMapping> findByShortCode(String shortCode);

    boolean getUrlMappingById(Long id);

    List<UrlMapping> Id(Long id);
}