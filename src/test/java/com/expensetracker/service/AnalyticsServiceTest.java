package com.expensetracker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.expensetracker.dto.response.CategorySummaryResponse;
import com.expensetracker.dto.response.MonthlySummaryResponse;
import com.expensetracker.entity.Expense;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.impl.AnalyticsServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {
    @Mock
    private ExpenseRepository expenseRepository;

    @Test
    void monthlySummaryShouldTotalExpensesForMonth() {
        when(expenseRepository.findByExpenseDateBetween(
                LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 30)))
                .thenReturn(List.of(expense("Food", "12.50"), expense("Travel", "20.00")));

        MonthlySummaryResponse response = new AnalyticsServiceImpl(expenseRepository)
                .getMonthlySummary(YearMonth.of(2026, 11));

        assertThat(response.getMonth()).isEqualTo("2026-11");
        assertThat(response.getTotalSpent()).isEqualByComparingTo("32.50");
    }

    @Test
    void categorySummaryShouldGroupExpensesByCategory() {
        when(expenseRepository.findByExpenseDateBetween(
                LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 30)))
                .thenReturn(List.of(expense("Food", "12.50"), expense("Food", "7.50")));

        List<CategorySummaryResponse> response = new AnalyticsServiceImpl(expenseRepository)
                .getCategorySummary(LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 30));

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getCategory()).isEqualTo("Food");
        assertThat(response.get(0).getTotalSpent()).isEqualByComparingTo("20.00");
    }

    private Expense expense(String category, String amount) {
        Expense expense = new Expense();
        expense.setCategory(category);
        expense.setAmount(new BigDecimal(amount));
        expense.setExpenseDate(LocalDate.of(2026, 11, 8));
        return expense;
    }
}
