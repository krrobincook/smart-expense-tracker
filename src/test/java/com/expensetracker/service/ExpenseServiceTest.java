package com.expensetracker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.expensetracker.dto.request.CreateExpenseRequest;
import com.expensetracker.dto.response.ExpenseResponse;
import com.expensetracker.entity.Expense;
import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.impl.ExpenseServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {
    @Mock
    private ExpenseRepository expenseRepository;

    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        expenseService = new ExpenseServiceImpl(expenseRepository);
    }

    @Test
    void createShouldPersistAndReturnExpense() {
        CreateExpenseRequest request = request();
        Expense saved = expense(1L);
        when(expenseRepository.save(any(Expense.class))).thenReturn(saved);

        ExpenseResponse response = expenseService.create(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getDescription()).isEqualTo("Lunch");
        verify(expenseRepository).save(any(Expense.class));
    }

    @Test
    void findByIdShouldThrowWhenExpenseDoesNotExist() {
        when(expenseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> expenseService.findById(99L))
                .isInstanceOf(ExpenseNotFoundException.class)
                .hasMessage("Expense not found with id: 99");
    }

    private CreateExpenseRequest request() {
        CreateExpenseRequest request = new CreateExpenseRequest();
        request.setDescription("Lunch");
        request.setAmount(new BigDecimal("12.50"));
        request.setCategory("Food");
        request.setExpenseDate(LocalDate.of(2026, 11, 8));
        return request;
    }

    private Expense expense(Long id) {
        Expense expense = new Expense();
        expense.setId(id);
        expense.setDescription("Lunch");
        expense.setAmount(new BigDecimal("12.50"));
        expense.setCategory("Food");
        expense.setExpenseDate(LocalDate.of(2026, 11, 8));
        return expense;
    }
}
