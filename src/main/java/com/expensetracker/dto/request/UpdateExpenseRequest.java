package com.expensetracker.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UpdateExpenseRequest {
    @NotBlank
    private String description;
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
    @NotBlank
    private String category;
    @NotNull
    private LocalDate expenseDate;
    private String notes;
}
