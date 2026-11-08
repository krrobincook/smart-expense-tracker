package com.expensetracker.service.impl;

import com.expensetracker.dto.request.CreateExpenseRequest;
import com.expensetracker.dto.request.UpdateExpenseRequest;
import com.expensetracker.dto.response.ExpenseResponse;
import com.expensetracker.entity.Expense;
import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.ExpenseService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    @Override
    public ExpenseResponse create(CreateExpenseRequest request) {
        Expense expense = new Expense();
        copy(request, expense);
        return toResponse(expenseRepository.save(expense));
    }

    @Override
    public List<ExpenseResponse> findAll() {
        return expenseRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public ExpenseResponse findById(Long id) {
        return toResponse(findExpense(id));
    }

    @Override
    public ExpenseResponse update(Long id, UpdateExpenseRequest request) {
        Expense expense = findExpense(id);
        copy(request, expense);
        return toResponse(expenseRepository.save(expense));
    }

    @Override
    public void delete(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException(id);
        }
        expenseRepository.deleteById(id);
    }

    private Expense findExpense(Long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException(id));
    }

    private void copy(CreateExpenseRequest request, Expense expense) {
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setNotes(request.getNotes());
    }

    private void copy(UpdateExpenseRequest request, Expense expense) {
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setNotes(request.getNotes());
    }

    private ExpenseResponse toResponse(Expense expense) {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(expense.getId());
        response.setDescription(expense.getDescription());
        response.setAmount(expense.getAmount());
        response.setCategory(expense.getCategory());
        response.setExpenseDate(expense.getExpenseDate());
        response.setNotes(expense.getNotes());
        return response;
    }
}
