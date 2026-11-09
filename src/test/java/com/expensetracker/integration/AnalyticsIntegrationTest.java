package com.expensetracker.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.expensetracker.dto.response.MonthlySummaryResponse;
import com.expensetracker.entity.Expense;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.AnalyticsService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AnalyticsIntegrationTest {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private AnalyticsService analyticsService;

    @BeforeEach
    void seedDatabase() {
        expenseRepository.deleteAll();
        expenseRepository.saveAll(List.of(
                expense("Food", "12.50", LocalDate.of(2026, 11, 8)),
                expense("Travel", "20.00", LocalDate.of(2026, 11, 9)),
                expense("Food", "8.00", LocalDate.of(2026, 10, 20))));
    }

    @Test
    void monthlySummaryShouldUsePersistedExpenses() {
        MonthlySummaryResponse response = analyticsService
                .getMonthlySummary(YearMonth.of(2026, 11));

        assertThat(response.getMonth()).isEqualTo("2026-11");
        assertThat(response.getTotalSpent()).isEqualByComparingTo("32.50");
    }

    private Expense expense(String category, String amount, LocalDate date) {
        Expense expense = new Expense();
        expense.setCategory(category);
        expense.setAmount(new BigDecimal(amount));
        expense.setDescription(category + " expense");
        expense.setExpenseDate(date);
        return expense;
    }
}
