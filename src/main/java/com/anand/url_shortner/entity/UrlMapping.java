package com.anand.url_shortner.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class UrlMapping {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private  User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String originalUrl;

    @Column(nullable = false, unique = true)
    private String shortCode;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;


    @OneToMany(mappedBy = "urlMapping")
    private List<Clickevent> clickEvents;

    @Column(nullable = false)
    private Long totalClicks = 0L;


}
