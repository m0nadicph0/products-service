package com.monadic.products.controller;

import com.monadic.products.dto.HealthResponse;
import com.monadic.products.service.HealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get health information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Health information retrieved", content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HealthResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
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
