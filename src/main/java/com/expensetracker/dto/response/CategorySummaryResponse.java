package com.expensetracker.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CategorySummaryResponse {
    private String category;
    private BigDecimal totalSpent;
}
