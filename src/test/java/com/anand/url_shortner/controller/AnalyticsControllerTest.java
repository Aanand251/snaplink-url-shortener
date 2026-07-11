package com.anand.url_shortner.controller;

import com.anand.url_shortner.auth.JwtFilter;
import com.anand.url_shortner.config.SecurityConfig;
import com.anand.url_shortner.dto.AnalyticsResponse;
import com.anand.url_shortner.filter.RateLimitFilter;
import com.anand.url_shortner.service.AnalyticsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = AnalyticsController.class,
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
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnalyticsService analyticsService;

    @Test
    @DisplayName("Analytics API Should Return 200")
    void getAnalytics_shouldReturn200() throws Exception {

        LocalDateTime lastClickedAt =
                LocalDateTime.of(
                        2026,
                        7,
                        12,
                        2,
                        30
                );

        AnalyticsResponse response =
                new AnalyticsResponse(
                        "abcd",
                        25L,
                        "Chrome",
                        "Desktop",
                        "India",
                        lastClickedAt
                );

        when(analyticsService.getAnalytics("abcd"))
                .thenReturn(response);

        mockMvc.perform(
                        get("/analytics/abcd")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.shortCode")
                                .value("abcd")
                )
                .andExpect(
                        jsonPath("$.clicks")
                                .value(25)
                )
                .andExpect(
                        jsonPath("$.topBrowser")
                                .value("Chrome")
                )
                .andExpect(
                        jsonPath("$.topDevice")
                                .value("Desktop")
                )
                .andExpect(
                        jsonPath("$.topCountry")
                                .value("India")
                )
                .andExpect(
                        jsonPath("$.lastClickedAt")
                                .value("2026-07-12T02:30:00")
                );

        verify(analyticsService)
                .getAnalytics("abcd");
    }

    @Test
    @DisplayName("Analytics API Should Return Empty Analytics")
    void getAnalytics_shouldReturnEmptyAnalytics()
            throws Exception {

        AnalyticsResponse response =
                new AnalyticsResponse(
                        "abcd",
                        0L,
                        null,
                        null,
                        null,
                        null
                );

        when(analyticsService.getAnalytics("abcd"))
                .thenReturn(response);

        mockMvc.perform(
                        get("/analytics/abcd")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.shortCode")
                                .value("abcd")
                )
                .andExpect(
                        jsonPath("$.clicks")
                                .value(0)
                )
                .andExpect(
                        jsonPath("$.topBrowser")
                                .doesNotExist()
                )
                .andExpect(
                        jsonPath("$.topDevice")
                                .doesNotExist()
                )
                .andExpect(
                        jsonPath("$.topCountry")
                                .doesNotExist()
                )
                .andExpect(
                        jsonPath("$.lastClickedAt")
                                .doesNotExist()
                );

        verify(analyticsService)
                .getAnalytics("abcd");
    }
}