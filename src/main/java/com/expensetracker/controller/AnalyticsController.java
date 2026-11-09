package com.expensetracker.controller;

import com.expensetracker.dto.response.CategorySummaryResponse;
import com.expensetracker.dto.response.MonthlySummaryResponse;
import com.expensetracker.dto.response.SpendingComparisonResponse;
import com.expensetracker.service.AnalyticsService;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }
    @GetMapping("/monthly")
    public ResponseEntity<MonthlySummaryResponse> getMonthlySummary(
            @RequestParam YearMonth month) {
        return ResponseEntity.ok(analyticsService.getMonthlySummary(month));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategorySummaryResponse>> getCategorySummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(analyticsService.getCategorySummary(startDate, endDate));
    }

    @GetMapping("/comparison")
    public ResponseEntity<SpendingComparisonResponse> getSpendingComparison(
            @RequestParam YearMonth month) {
        return ResponseEntity.ok(analyticsService.getSpendingComparison(month));
    }
}
