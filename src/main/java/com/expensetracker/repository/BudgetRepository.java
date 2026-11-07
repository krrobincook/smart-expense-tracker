package com.expensetracker.repository;

import com.expensetracker.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    // Query methods will be added with the budget use cases.
}
