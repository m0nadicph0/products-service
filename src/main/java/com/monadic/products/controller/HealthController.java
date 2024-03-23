package com.monadic.products.controller;

import com.monadic.products.dto.HealthResponse;
import com.monadic.products.service.HealthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public ResponseEntity<HealthResponse> checkHealth() {
        String dbConnection = healthService.getDbStatus();
        String version = healthService.getAppVersion();
        String status = dbConnection.equals("connected")? "healthy":"unhealthy";

        HealthResponse healthResponse = HealthResponse.builder()
                .version(version)
                .status(status)
                .dbConnection(dbConnection)
                .build();
        return new ResponseEntity<>(healthResponse, HttpStatus.OK);
    }
}
