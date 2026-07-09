package com.anand.url_shortner.filter;

import com.anand.url_shortner.service.RateLimiterService;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateLimitFilterTest {

    @Mock
    private RateLimiterService rateLimiterService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private RateLimitFilter rateLimitFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        request.setRemoteAddr("127.0.0.1");
    }

    @Test
    @DisplayName("Allowed Request Should Continue Filter Chain")
    void allowedRequest_shouldContinueFilterChain() throws Exception {

        when(rateLimiterService.isAllowed("127.0.0.1"))
                .thenReturn(true);

        rateLimitFilter.doFilter(request, response, filterChain);

        verify(filterChain)
                .doFilter(request, response);

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Blocked Request Should Return 429")
    void blockedRequest_shouldReturn429() throws Exception {

        when(rateLimiterService.isAllowed("127.0.0.1"))
                .thenReturn(false);

        rateLimitFilter.doFilter(request, response, filterChain);

        assertEquals(429, response.getStatus());

        assertTrue(
                response.getContentAsString()
                        .contains("Too Many Requests")
        );

        verify(filterChain, never())
                .doFilter(any(), any());
    }

    @Test
    @DisplayName("Blocked Request Should Return JSON Content Type")
    void blockedRequest_shouldReturnJsonContentType() throws Exception {

        when(rateLimiterService.isAllowed("127.0.0.1"))
                .thenReturn(false);

        rateLimitFilter.doFilter(request, response, filterChain);

        assertEquals(
                "application/json",
                response.getContentType()
        );
    }

}