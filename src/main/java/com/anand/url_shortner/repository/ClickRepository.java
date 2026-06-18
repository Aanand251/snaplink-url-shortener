package com.anand.url_shortner.repository;

import com.anand.url_shortner.entity.Clickevent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickRepository
        extends JpaRepository<Clickevent, Long> {

    long countByUrlMapping_ShortCode(String shortCode);
}