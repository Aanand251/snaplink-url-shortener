package com.anand.url_shortner.repository;

import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.anand.url_shortner.repository.projection.TopLinkProjection;


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

    List<UrlMapping> findByExpiresAtBefore(LocalDateTime time);
    void deleteByExpiresAtBefore(LocalDateTime time);

    @Query("""
SELECT
u.shortCode AS shortCode,
u.originalUrl AS originalUrl,
u.totalClicks AS totalClicks
FROM UrlMapping u
WHERE u.user.id = :userId
ORDER BY u.totalClicks DESC
LIMIT 5
""")
    List<TopLinkProjection> findTopLinksByUser(Long userId);
}