package com.expensetracker.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.expensetracker.dto.response.MonthlySummaryResponse;
import com.expensetracker.exception.GlobalExceptionHandler;
import com.expensetracker.service.AnalyticsService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AnalyticsControllerTest {
    @Mock
    private AnalyticsService analyticsService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AnalyticsController(analyticsService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void monthlySummaryShouldReturnTotal() throws Exception {
        MonthlySummaryResponse response = new MonthlySummaryResponse();
        response.setMonth("2026-11");
        response.setTotalSpent(new BigDecimal("32.50"));
        when(analyticsService.getMonthlySummary(java.time.YearMonth.of(2026, 11))).thenReturn(response);

        mockMvc.perform(get("/api/analytics/monthly?month=2026-11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.month").value("2026-11"))
                .andExpect(jsonPath("$.totalSpent").value(32.50));
    }
}
