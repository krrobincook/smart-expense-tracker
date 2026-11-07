package com.expensetracker.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SpendingComparisonResponse {
    private String currentPeriod;
    private BigDecimal currentTotal;
    private String previousPeriod;
    private BigDecimal previousTotal;
}
