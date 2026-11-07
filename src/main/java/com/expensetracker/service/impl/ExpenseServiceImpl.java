package com.expensetracker.service.impl;

import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.ExpenseService;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    // Business logic will be implemented here.
}
