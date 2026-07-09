package com.anand.url_shortner.controller;

import com.anand.url_shortner.auth.JwtFilter;
import com.anand.url_shortner.config.SecurityConfig;
import com.anand.url_shortner.filter.RateLimitFilter;
import com.anand.url_shortner.service.ClickTrackingService;
import com.anand.url_shortner.service.RedirectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = RedirectController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                ),
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = JwtFilter.class
                ),
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = RateLimitFilter.class
                )
        }
)
@AutoConfigureMockMvc(addFilters = false)
class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RedirectService redirectService;

    @MockitoBean
    private ClickTrackingService clickTrackingService;

    @Test
    @DisplayName("Application Context Should Load")
    void contextLoads() {

    }

    @Test
    @DisplayName("Redirect API Should Return 302 With Location Header")
    void redirect_shouldReturn302() throws Exception {

        when(redirectService.getOriginalUrl("abcd"))
                .thenReturn("https://google.com");

        mockMvc.perform(get("/r/abcd")
                        .header("User-Agent", "Chrome"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://google.com"));

        verify(clickTrackingService)
                .saveClick(
                        eq("abcd"),
                        eq("Chrome"),
                        eq("127.0.0.1")
                );
    }

    @Test
    @DisplayName("Redirect Should Work Even Without User-Agent Header")
    void redirect_withoutUserAgent_shouldStillRedirect() throws Exception {

        when(redirectService.getOriginalUrl("abcd"))
                .thenReturn("https://google.com");

        mockMvc.perform(get("/r/abcd"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://google.com"));

        verify(clickTrackingService)
                .saveClick(
                        eq("abcd"),
                        isNull(),
                        eq("127.0.0.1")
                );
    }
}