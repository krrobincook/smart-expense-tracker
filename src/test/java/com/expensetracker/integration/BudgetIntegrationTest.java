package com.expensetracker.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.expensetracker.dto.request.CreateBudgetRequest;
import com.expensetracker.dto.response.BudgetResponse;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.service.BudgetService;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BudgetIntegrationTest {
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;

    @BeforeEach
    void cleanDatabase() {
        budgetRepository.deleteAll();
    }

    @Test
    void creatingBudgetShouldPersistItInDatabase() {
        CreateBudgetRequest request = new CreateBudgetRequest();
        request.setCategory("Food");
        request.setAmount(new BigDecimal("500.00"));
        request.setStartDate(LocalDate.of(2026, 11, 1));
        request.setEndDate(LocalDate.of(2026, 11, 30));

        BudgetResponse response = budgetService.create(request);

        assertThat(budgetRepository.findById(response.getId())).isPresent();
        assertThat(budgetRepository.findById(response.getId()).orElseThrow().getCategory())
                .isEqualTo("Food");
    }
}
