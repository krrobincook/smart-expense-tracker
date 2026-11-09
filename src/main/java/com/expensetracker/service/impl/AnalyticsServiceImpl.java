package com.expensetracker.service.impl;

import com.expensetracker.dto.response.CategorySummaryResponse;
import com.expensetracker.dto.response.MonthlySummaryResponse;
import com.expensetracker.dto.response.SpendingComparisonResponse;
import com.expensetracker.entity.Expense;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.AnalyticsService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private final ExpenseRepository expenseRepository;

    public AnalyticsServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public MonthlySummaryResponse getMonthlySummary(YearMonth month) {
        List<Expense> expenses = findExpenses(month.atDay(1), month.atEndOfMonth());
        MonthlySummaryResponse response = new MonthlySummaryResponse();
        response.setMonth(month.toString());
        response.setTotalSpent(total(expenses));
        return response;
    }

    @Override
    public List<CategorySummaryResponse> getCategorySummary(LocalDate startDate, LocalDate endDate) {
        Map<String, BigDecimal> totals = findExpenses(startDate, endDate).stream()
                .collect(Collectors.groupingBy(Expense::getCategory,
                        Collectors.mapping(Expense::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        return totals.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    CategorySummaryResponse response = new CategorySummaryResponse();
                    response.setCategory(entry.getKey());
                    response.setTotalSpent(entry.getValue());
                    return response;
                })
                .toList();
    }

    @Override
    public SpendingComparisonResponse getSpendingComparison(YearMonth currentMonth) {
        YearMonth previousMonth = currentMonth.minusMonths(1);
        SpendingComparisonResponse response = new SpendingComparisonResponse();
        response.setCurrentPeriod(currentMonth.toString());
        response.setCurrentTotal(getMonthlySummary(currentMonth).getTotalSpent());
        response.setPreviousPeriod(previousMonth.toString());
        response.setPreviousTotal(getMonthlySummary(previousMonth).getTotalSpent());
        return response;
    }

    private List<Expense> findExpenses(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByExpenseDateBetween(startDate, endDate);
    }

    private BigDecimal total(List<Expense> expenses) {
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
