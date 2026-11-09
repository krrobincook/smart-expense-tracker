package com.expensetracker.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.expensetracker.dto.request.CreateExpenseRequest;
import com.expensetracker.dto.response.ExpenseResponse;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.ExpenseService;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ExpenseIntegrationTest {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseService expenseService;

    @BeforeEach
    void cleanDatabase() {
        expenseRepository.deleteAll();
    }

    @Test
    void creatingExpenseShouldPersistItInDatabase() {
        CreateExpenseRequest request = new CreateExpenseRequest();
        request.setDescription("Integration lunch");
        request.setAmount(new BigDecimal("18.75"));
        request.setCategory("Food");
        request.setExpenseDate(LocalDate.of(2026, 11, 9));

        ExpenseResponse response = expenseService.create(request);

        assertThat(expenseRepository.findById(response.getId())).isPresent();
        assertThat(expenseRepository.findById(response.getId()).orElseThrow().getDescription())
                .isEqualTo("Integration lunch");
    }
}
