package com.monadic.products;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthIntegrationTest {
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    public void testHealthIntegration() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/health")
                .then()
                .statusCode(200)
                .body("version", equalTo("development"))
                .body("status", equalTo("healthy"))
                .body("dbConnection", equalTo("connected"));
    }
}
