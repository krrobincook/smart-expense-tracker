package com.expensetracker.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ExpenseResponse {
    private Long id;
    private String description;
    private BigDecimal amount;
    private String category;
    private LocalDate expenseDate;
    private String notes;
}
