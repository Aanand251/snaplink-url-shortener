package com.anand.url_shortner.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Clickevent {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id
   private  Long  id;

    private String device;
    private String country;
    private String browser;
    private LocalDateTime clickedAt;


    @ManyToOne
    @JoinColumn(name = "url_id")
    private  UrlMapping  urlMapping;

}
