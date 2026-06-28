package com.anand.url_shortner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
       private   long id;

          @Column(nullable = false)
          private String name;

           @Column(nullable = false,
            unique = true)
            private String email;


           @Column(nullable = false)
            private String password;


            @Column(nullable = false)
            private LocalDateTime createdAt;

}
