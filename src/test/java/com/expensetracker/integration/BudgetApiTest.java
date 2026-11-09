package com.expensetracker.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.expensetracker.repository.BudgetRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BudgetApiTest {
    @LocalServerPort
    private int port;

    @Autowired
    private BudgetRepository budgetRepository;

    @BeforeEach
    void cleanDatabase() {
        budgetRepository.deleteAll();
    }

    @Test
    void shouldCreateBudgetThroughHttpApi() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "category": "Food",
                          "amount": 500.00,
                          "startDate": "2026-11-01",
                          "endDate": "2026-11-30"
                        }
                        """)
                .when()
                .post("http://localhost:" + port + "/api/budgets")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("category", equalTo("Food"))
                .body("amount", equalTo(500.0F))
                .header("Location", notNullValue());
    }
}
