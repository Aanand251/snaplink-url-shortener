package com.anand.url_shortner.repository;

import com.anand.url_shortner.entity.Clickevent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface ClickRepository
        extends JpaRepository<Clickevent, Long> {

    long countByUrlMapping_ShortCode(String shortCode);


    @Query(value = """
            SELECT browser
            FROM clickevent
            JOIN url_mapping
            ON clickevent.url_id = url_mapping.id
            WHERE url_mapping.short_code = :shortCode
            GROUP BY browser
            ORDER BY COUNT(browser) DESC
            LIMIT 1
            """, nativeQuery = true)
    String findTopBrowser(String shortCode);

    @Query(value = """
            SELECT device
            FROM clickevent
            JOIN url_mapping
            ON clickevent.url_id = url_mapping.id
            WHERE url_mapping.short_code = :shortCode
            GROUP BY device
            ORDER BY COUNT(device) DESC
            LIMIT 1
            """, nativeQuery = true)
    String findTopDevice(String shortCode);

    @Query(value = """
        SELECT MAX(clicked_at)
        FROM clickevent
        JOIN url_mapping
        ON clickevent.url_id = url_mapping.id
        WHERE url_mapping.short_code = :shortCode
        """, nativeQuery = true)
    LocalDateTime findLastClickedAt(String shortCode);

    @Query(value = """
        SELECT country
        FROM clickevent
        JOIN url_mapping
        ON clickevent.url_id = url_mapping.id
        WHERE url_mapping.short_code = :shortCode
        GROUP BY country
        ORDER BY COUNT(country) DESC
        LIMIT 1
        """, nativeQuery = true)
    String findTopCountry(String shortCode);
}