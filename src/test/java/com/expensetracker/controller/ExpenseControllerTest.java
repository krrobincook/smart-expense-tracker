package com.expensetracker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.expensetracker.dto.request.CreateExpenseRequest;
import com.expensetracker.dto.response.ExpenseResponse;
import com.expensetracker.exception.GlobalExceptionHandler;
import com.expensetracker.service.ExpenseService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {
    @Mock
    private ExpenseService expenseService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ExpenseController(expenseService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createShouldReturnCreatedExpense() throws Exception {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(1L);
        response.setDescription("Lunch");
        response.setAmount(new BigDecimal("12.50"));
        response.setCategory("Food");
        response.setExpenseDate(LocalDate.of(2026, 11, 8));
        when(expenseService.create(any(CreateExpenseRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "description": "Lunch",
                                  "amount": 12.50,
                                  "category": "Food",
                                  "expenseDate": "2026-11-08"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/expenses/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Lunch"));
    }
}
