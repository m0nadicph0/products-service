package com.monadic.products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HealthResponse {
    private String version;
    private String status;
    private String dbConnection;
}
