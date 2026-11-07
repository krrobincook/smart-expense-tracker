package com.expensetracker.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class BudgetResponse {
    private Long id;
    private String category;
    private BigDecimal amount;
    private LocalDate startDate;
    private LocalDate endDate;
}
