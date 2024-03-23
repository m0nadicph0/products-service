package com.monadic.products.controller;

import com.monadic.products.dto.HealthResponse;
import com.monadic.products.service.HealthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class HealthControllerTest {

    @InjectMocks
    HealthController healthController;

    @Mock
    HealthService healthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void getHealthStatus() {
        when(healthService.getDbStatus()).thenReturn("connected");
        when(healthService.getAppVersion()).thenReturn("0.0.1-37b9739");

        ResponseEntity<HealthResponse> responseEntity = healthController.checkHealth();

        assertEquals("connected", Objects.requireNonNull(responseEntity.getBody()).getDbConnection());
        assertEquals("healthy", Objects.requireNonNull(responseEntity.getBody()).getStatus());
        assertEquals("0.0.1-37b9739", Objects.requireNonNull(responseEntity.getBody()).getVersion());
    }
}