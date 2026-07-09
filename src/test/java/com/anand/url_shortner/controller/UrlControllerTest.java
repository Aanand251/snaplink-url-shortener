package com.anand.url_shortner.controller;

import com.anand.url_shortner.entity.UrlMapping;
import com.anand.url_shortner.repository.ClickRepository;
import com.anand.url_shortner.service.ClickTrackingService;
import com.anand.url_shortner.service.DashboardService;
import com.anand.url_shortner.service.UrlService;
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
import com.anand.url_shortner.auth.JwtFilter;
import com.anand.url_shortner.config.SecurityConfig;
import com.anand.url_shortner.filter.RateLimitFilter;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.anand.url_shortner.dto.CreateUrlRequest;
import com.anand.url_shortner.dto.DashboardResponse;
import com.anand.url_shortner.dto.UpdateUrlRequest;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(
        controllers = UrlController.class,
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
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UrlService urlService;

    @MockitoBean
    private ClickTrackingService clickTrackingService;

    @MockitoBean
    private DashboardService dashboardService;

    @MockitoBean
    private ClickRepository clickRepository;

    @Test
    @DisplayName("Application Context Should Load")
    void contextLoads() {

    }
    @Test
    @DisplayName("Get All URLs Should Return 200")
    void getAllUrls_shouldReturn200() throws Exception {

        UrlMapping url = new UrlMapping();
        url.setId(1L);
        url.setOriginalUrl("https://google.com");
        url.setShortCode("abcd");

        List<UrlMapping> urls = List.of(url);

        when(urlService.getAllUrls())
                .thenReturn(urls);

        mockMvc.perform(get("/api/url"))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Create Short URL Should Return 200")
    void createShortUrl_shouldReturn200() throws Exception {

        CreateUrlRequest request = new CreateUrlRequest();
        request.setOriginalUrl("https://google.com");

        when(urlService.createShortUrl(any(CreateUrlRequest.class)))
                .thenReturn("abcd");

        mockMvc.perform(post("/api/url/shorten")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("abcd"));
    }

    @Test
    @DisplayName("Analytics API Should Return 200")
    void getAnalytics_shouldReturn200() throws Exception {

        when(clickTrackingService.getClickCount("abcd"))
                .thenReturn(25L);

        when(clickRepository.findTopBrowser("abcd"))
                .thenReturn("Chrome");

        when(clickRepository.findTopDevice("abcd"))
                .thenReturn("Desktop");

        mockMvc.perform(get("/api/url/analytics/abcd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortCode").value("abcd"))
                .andExpect(jsonPath("$.clicks").value(25))
                .andExpect(jsonPath("$.topBrowser").value("Chrome"))
                .andExpect(jsonPath("$.topDevice").value("Desktop"));
    }

    @Test
    @DisplayName("Dashboard API Should Return 200")
    void getDashboard_shouldReturn200() throws Exception {

        DashboardResponse response = new DashboardResponse(
                "https://google.com",
                "abcd",
                100,
                "Chrome",
                "Desktop",
                "India",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(dashboardService.getDashboard("abcd"))
                .thenReturn(response);

        mockMvc.perform(get("/api/url/dashboard/abcd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl")
                        .value("https://google.com"))
                .andExpect(jsonPath("$.shortCode")
                        .value("abcd"))
                .andExpect(jsonPath("$.totalClicks")
                        .value(100))
                .andExpect(jsonPath("$.topBrowser")
                        .value("Chrome"))
                .andExpect(jsonPath("$.topDevice")
                        .value("Desktop"))
                .andExpect(jsonPath("$.topCountry")
                        .value("India"));
    }


    @Test
    @DisplayName("Get My URLs Should Return 200")
    void getMyUrls_shouldReturn200() throws Exception {

        when(urlService.getMyUrls())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/url/my-urls"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete URL Should Return 200")
    void deleteUrl_shouldReturn200() throws Exception {

        mockMvc.perform(delete("/api/url/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("URL deleted successfully"));
    }

    @Test
    @DisplayName("Update URL Should Return 200")
    void updateUrl_shouldReturn200() throws Exception {

        UpdateUrlRequest request = new UpdateUrlRequest();
        request.setOriginalUrl("https://openai.com");

        mockMvc.perform(put("/api/url/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("URL updated successfully"));
    }




}