package com.expensetracker.service;

import com.expensetracker.dto.request.CreateBudgetRequest;
import com.expensetracker.dto.request.UpdateBudgetRequest;
import com.expensetracker.dto.response.BudgetResponse;
import java.util.List;

public interface BudgetService {
    BudgetResponse create(CreateBudgetRequest request);
    List<BudgetResponse> findAll();
    BudgetResponse findById(Long id);
    BudgetResponse update(Long id, UpdateBudgetRequest request);
    void delete(Long id);
}
