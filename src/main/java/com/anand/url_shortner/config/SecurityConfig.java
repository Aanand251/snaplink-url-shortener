package com.anand.url_shortner.config;

import com.anand.url_shortner.auth.CustomUserDetailsService;
import com.anand.url_shortner.auth.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
  public class SecurityConfig{

    private final JwtFilter jwtFilter;
 @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                                "/api/auth/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                 .addFilterBefore(
             jwtFilter,
             UsernamePasswordAuthenticationFilter.class
     )
                .httpBasic(Customizer.withDefaults());

            return http.build();
    }

    //BEAN-1
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
     DaoAuthenticationProvider provider  = new DaoAuthenticationProvider(userDetailsService);
     provider.setPasswordEncoder(passwordEncoder);
     return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}
