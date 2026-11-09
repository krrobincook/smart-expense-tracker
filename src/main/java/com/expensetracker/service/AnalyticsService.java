package com.expensetracker.service;

import com.expensetracker.dto.response.CategorySummaryResponse;
import com.expensetracker.dto.response.MonthlySummaryResponse;
import com.expensetracker.dto.response.SpendingComparisonResponse;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface AnalyticsService {
    MonthlySummaryResponse getMonthlySummary(YearMonth month);
    List<CategorySummaryResponse> getCategorySummary(LocalDate startDate, LocalDate endDate);
    SpendingComparisonResponse getSpendingComparison(YearMonth currentMonth);
}
