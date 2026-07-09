package com.anand.url_shortner.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

   @Override
   protected  void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
       System.out.println("JWT Filter Executed");

       final String authorizationHeader = request.getHeader("Authorization");
       if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
           filterChain.doFilter(request, response);
           return;
       }

       String token = authorizationHeader.substring(7);
       String email = jwtService.extractEmail(token);
       if (email != null &&
        SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails userDetails = userDetailsService.loadUserByUsername(email);

           if(jwtService.isTokenValid(token , userDetails.getUsername())){
               System.out.println("=================================");
               System.out.println("User      : " + userDetails.getUsername());
               System.out.println("Authorities : " + userDetails.getAuthorities());
               System.out.println("=================================");
               UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                       userDetails,
                       null,
                       userDetails.getAuthorities()
               );

               authentication.setDetails(
                       new WebAuthenticationDetailsSource().
                               buildDetails(request)
               );
               SecurityContextHolder
                       .getContext()
                       .setAuthentication(authentication);
           }
       }
       filterChain.doFilter(request, response);
    }
}
