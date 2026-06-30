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
    private String  originalUrl;
    private String  shortCode;
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "urlMapping")
    private List<Clickevent> clickEvents;
}
