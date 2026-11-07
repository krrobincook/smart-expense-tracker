package com.expensetracker.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UpdateExpenseRequest {
    private String description;
    private BigDecimal amount;
    private String category;
    private LocalDate expenseDate;
    private String notes;
}
