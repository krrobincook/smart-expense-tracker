package com.expensetracker.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.expensetracker.repository.ExpenseRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ExpenseApiTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        expenseRepository.deleteAll();
    }

    @Test
    void shouldCreateAndReadExpenseThroughHttpApi() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "description": "API lunch",
                          "amount": 22.50,
                          "category": "Food",
                          "expenseDate": "2026-11-09"
                        }
                        """)
                .when()
                .post("http://localhost:" + port + "/api/expenses")
                .then()
                .statusCode(201)
                .body("description", equalTo("API lunch"))
                .body("id", notNullValue())
                .header("Location", notNullValue());
    }
}
