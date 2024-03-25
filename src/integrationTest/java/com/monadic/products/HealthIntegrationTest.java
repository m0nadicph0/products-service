package com.monadic.products;

import com.monadic.products.dto.HealthResponse;
import com.monadic.products.model.Product;
import com.monadic.products.util.UrlUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class HealthIntegrationTest {
    @LocalServerPort
    private Integer port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:latest"
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    public void testCheckHealth() {
        ResponseEntity<HealthResponse> responseEntity = restTemplate.getForEntity(UrlUtil.urlFor("health", port), HealthResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("development", Objects.requireNonNull(responseEntity.getBody()).getVersion());
        assertEquals("healthy", responseEntity.getBody().getStatus());
        assertEquals("connected", responseEntity.getBody().getDbConnection());
    }
}
