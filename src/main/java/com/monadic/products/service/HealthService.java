package com.monadic.products.service;

import java.sql.SQLException;

public interface HealthService {
    String getDbStatus();
    String getAppVersion();
}