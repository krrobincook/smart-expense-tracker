package com.expensetracker.controller;

import com.expensetracker.dto.request.CreateExpenseRequest;
import com.expensetracker.dto.request.UpdateExpenseRequest;
import com.expensetracker.dto.response.ExpenseResponse;
import com.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @PostMapping
    public ResponseEntity<ExpenseResponse> create(@Valid @RequestBody CreateExpenseRequest request) {
        ExpenseResponse response = expenseService.create(request);
        return ResponseEntity.created(URI.create("/api/expenses/" + response.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> findAll() {
        return ResponseEntity.ok(expenseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateExpenseRequest request) {
        return ResponseEntity.ok(expenseService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
