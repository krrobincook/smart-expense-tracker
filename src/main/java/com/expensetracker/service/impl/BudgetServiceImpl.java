package com.expensetracker.service.impl;

import com.expensetracker.dto.request.CreateBudgetRequest;
import com.expensetracker.dto.request.UpdateBudgetRequest;
import com.expensetracker.dto.response.BudgetResponse;
import com.expensetracker.entity.Budget;
import com.expensetracker.exception.BudgetNotFoundException;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.service.BudgetService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }
    @Override
    public BudgetResponse create(CreateBudgetRequest request) {
        Budget budget = new Budget();
        copy(request, budget);
        return toResponse(budgetRepository.save(budget));
    }

    @Override
    public List<BudgetResponse> findAll() {
        return budgetRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public BudgetResponse findById(Long id) {
        return toResponse(findBudget(id));
    }

    @Override
    public BudgetResponse update(Long id, UpdateBudgetRequest request) {
        Budget budget = findBudget(id);
        copy(request, budget);
        return toResponse(budgetRepository.save(budget));
    }

    @Override
    public void delete(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new BudgetNotFoundException(id);
        }
        budgetRepository.deleteById(id);
    }

    private Budget findBudget(Long id) {
        return budgetRepository.findById(id).orElseThrow(() -> new BudgetNotFoundException(id));
    }

    private void copy(CreateBudgetRequest request, Budget budget) {
        budget.setCategory(request.getCategory());
        budget.setAmount(request.getAmount());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());
    }

    private void copy(UpdateBudgetRequest request, Budget budget) {
        copy((CreateBudgetRequest) request, budget);
    }

    private BudgetResponse toResponse(Budget budget) {
        BudgetResponse response = new BudgetResponse();
        response.setId(budget.getId());
        response.setCategory(budget.getCategory());
        response.setAmount(budget.getAmount());
        response.setStartDate(budget.getStartDate());
        response.setEndDate(budget.getEndDate());
        return response;
    }
}
