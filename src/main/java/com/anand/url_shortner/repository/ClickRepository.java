package com.anand.url_shortner.repository;

import com.anand.url_shortner.entity.Clickevent;
import com.anand.url_shortner.repository.projection.BrowserStatProjection;
import com.anand.url_shortner.repository.projection.DailyClickProjection;
import com.anand.url_shortner.repository.projection.RecentActivityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


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

    @Query(value = """
SELECT
browser AS browser,
COUNT(*) AS clicks
FROM clickevent
JOIN url_mapping
ON clickevent.url_id=url_mapping.id
WHERE url_mapping.short_code=:shortCode
GROUP BY browser
ORDER BY clicks DESC
""", nativeQuery = true)
    List<BrowserStatProjection> getBrowserStats(String shortCode);

    @Query(value = """
SELECT
browser AS browser,
device AS device,
country AS country,
clicked_at AS clickedAt
FROM clickevent
JOIN url_mapping
ON clickevent.url_id=url_mapping.id
WHERE url_mapping.short_code=:shortCode
ORDER BY clicked_at DESC
LIMIT 10
""", nativeQuery = true)
    List<RecentActivityProjection> getRecentActivities(String shortCode);


    @Query(value = """
SELECT COUNT(*)
FROM clickevent
JOIN url_mapping
ON clickevent.url_id=url_mapping.id
WHERE url_mapping.short_code=:shortCode
AND DATE(clicked_at)=CURRENT_DATE
""", nativeQuery = true)
    long getTodayClicks(String shortCode);

    @Query(value = """
SELECT
DATE(clicked_at) AS date,
COUNT(*) AS clicks
FROM clickevent
JOIN url_mapping
ON clickevent.url_id=url_mapping.id
WHERE url_mapping.short_code=:shortCode
AND clicked_at>=CURRENT_DATE-INTERVAL '6 days'
GROUP BY DATE(clicked_at)
ORDER BY DATE(clicked_at)
""", nativeQuery = true)
    List<DailyClickProjection> getDailyClicks(String shortCode);
}