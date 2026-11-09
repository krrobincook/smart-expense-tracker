package com.expensetracker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.expensetracker.dto.request.CreateBudgetRequest;
import com.expensetracker.dto.response.BudgetResponse;
import com.expensetracker.entity.Budget;
import com.expensetracker.exception.BudgetNotFoundException;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.service.impl.BudgetServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {
    @Mock
    private BudgetRepository budgetRepository;

    private BudgetService budgetService;

    @BeforeEach
    void setUp() {
        budgetService = new BudgetServiceImpl(budgetRepository);
    }

    @Test
    void createShouldPersistAndReturnBudget() {
        when(budgetRepository.save(any(Budget.class))).thenReturn(budget(1L));

        BudgetResponse response = budgetService.create(request());

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getCategory()).isEqualTo("Food");
        verify(budgetRepository).save(any(Budget.class));
    }

    @Test
    void findByIdShouldThrowWhenBudgetDoesNotExist() {
        when(budgetRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> budgetService.findById(99L))
                .isInstanceOf(BudgetNotFoundException.class)
                .hasMessage("Budget not found with id: 99");
    }

    private CreateBudgetRequest request() {
        CreateBudgetRequest request = new CreateBudgetRequest();
        request.setCategory("Food");
        request.setAmount(new BigDecimal("500.00"));
        request.setStartDate(LocalDate.of(2026, 11, 1));
        request.setEndDate(LocalDate.of(2026, 11, 30));
        return request;
    }

    private Budget budget(Long id) {
        Budget budget = new Budget();
        budget.setId(id);
        budget.setCategory("Food");
        budget.setAmount(new BigDecimal("500.00"));
        budget.setStartDate(LocalDate.of(2026, 11, 1));
        budget.setEndDate(LocalDate.of(2026, 11, 30));
        return budget;
    }
}
