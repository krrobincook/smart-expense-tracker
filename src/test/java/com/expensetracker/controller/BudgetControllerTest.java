package com.expensetracker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.expensetracker.dto.request.CreateBudgetRequest;
import com.expensetracker.dto.response.BudgetResponse;
import com.expensetracker.exception.GlobalExceptionHandler;
import com.expensetracker.service.BudgetService;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class BudgetControllerTest {
    @Mock
    private BudgetService budgetService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BudgetController(budgetService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createShouldReturnCreatedBudget() throws Exception {
        BudgetResponse response = new BudgetResponse();
        response.setId(1L);
        response.setCategory("Food");
        response.setAmount(new BigDecimal("500.00"));
        response.setStartDate(LocalDate.of(2026, 11, 1));
        response.setEndDate(LocalDate.of(2026, 11, 30));
        when(budgetService.create(any(CreateBudgetRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "category": "Food",
                                  "amount": 500.00,
                                  "startDate": "2026-11-01",
                                  "endDate": "2026-11-30"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/budgets/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value("Food"));
    }
}
