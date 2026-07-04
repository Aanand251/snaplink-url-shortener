package com.anand.url_shortner.repository;

import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface UrlRepository
        extends JpaRepository<UrlMapping , Long>{

    @Transactional
    @Modifying
    @Query("""
UPDATE UrlMapping u
SET u.totalClicks = u.totalClicks + :clickCount
WHERE u.shortCode = :shortCode
""")
    int incrementTotalClicks(
            @Param("shortCode") String shortCode,
            @Param("clickCount") Long clickCount
    );

    Optional<UrlMapping> findByShortCode(String shortCode);

    List<UrlMapping> findByUser(User user);

    Optional<UrlMapping> findByIdAndUser(Long id, User user);


}