package com.expensetracker.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.expensetracker.entity.Expense;
import com.expensetracker.repository.ExpenseRepository;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AnalyticsApiTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void seedDatabase() {
        expenseRepository.deleteAll();
        expenseRepository.saveAll(List.of(
                expense("Food", "12.50", LocalDate.of(2026, 11, 8)),
                expense("Travel", "20.00", LocalDate.of(2026, 11, 9))));
    }

    @Test
    void monthlySummaryShouldBeAvailableThroughHttpApi() {
        given()
                .accept(ContentType.JSON)
                .when()
                .request(Method.GET, "http://localhost:" + port + "/api/analytics/monthly?month=2026-11")
                .then()
                .statusCode(200)
                .body("month", equalTo("2026-11"))
                .body("totalSpent", equalTo(32.5F));
    }

    private Expense expense(String category, String amount, LocalDate date) {
        Expense expense = new Expense();
        expense.setCategory(category);
        expense.setAmount(new BigDecimal(amount));
        expense.setDescription(category + " expense");
        expense.setExpenseDate(date);
        return expense;
    }
}
