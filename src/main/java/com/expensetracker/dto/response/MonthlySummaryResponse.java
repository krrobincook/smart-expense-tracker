package com.expensetracker.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MonthlySummaryResponse {
    private String month;
    private BigDecimal totalSpent;
}
