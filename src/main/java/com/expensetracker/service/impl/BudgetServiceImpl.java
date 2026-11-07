package com.expensetracker.service.impl;

import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.service.BudgetService;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }
    // Business logic will be implemented here.
}
