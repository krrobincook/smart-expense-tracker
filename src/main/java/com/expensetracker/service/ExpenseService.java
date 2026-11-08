package com.expensetracker.service;

import com.expensetracker.dto.request.CreateExpenseRequest;
import com.expensetracker.dto.request.UpdateExpenseRequest;
import com.expensetracker.dto.response.ExpenseResponse;
import java.util.List;

public interface ExpenseService {
    ExpenseResponse create(CreateExpenseRequest request);
    List<ExpenseResponse> findAll();
    ExpenseResponse findById(Long id);
    ExpenseResponse update(Long id, UpdateExpenseRequest request);
    void delete(Long id);
}
