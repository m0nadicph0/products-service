package com.monadic.products.service.impl;

import com.monadic.products.ProductsServiceApplication;
import com.monadic.products.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class HealthServiceImpl implements HealthService {

    @Autowired
    private DataSource dataSource;

    @Override
    public String getDbStatus() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                return "connected";
            }
        } catch (SQLException exception) {
            return "disconnected";
        }
        return "disconnected";
    }

    @Override
    public String getAppVersion() {
        return Optional.ofNullable(ProductsServiceApplication.class.getPackage().getImplementationVersion()).orElse("development");
    }
}
