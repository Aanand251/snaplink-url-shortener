package com.anand.url_shortner.filter;

import com.anand.url_shortner.service.RateLimiterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;
    private static final int TOO_MANY_REQUESTS = 429;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();

        if (!rateLimiterService.isAllowed(clientIp)) {

            response.setStatus(TOO_MANY_REQUESTS);
            response.setContentType("application/json");

            response.getWriter().write("""
                    {
                      "success": false,
                      "message": "Too Many Requests"
                    }
                    """);

            return;
        }

        filterChain.doFilter(request, response);
    }
}