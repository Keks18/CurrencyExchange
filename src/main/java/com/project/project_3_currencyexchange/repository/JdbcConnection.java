package com.project.project_3_currencyexchange.repository;

import com.project.project_3_currencyexchange.exceptions.ConnectionExceptions;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcConnection implements AutoCloseable {
    Connection connection;

    public JdbcConnection() throws SQLException {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream("/db.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            ConnectionExceptions.handlePropertiesLoadingFailure(e);
        }

        String jdbcUrl = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        String driverName = properties.getProperty("db.driverName");
        try {
            Class.forName(driverName);

            connection = DriverManager.getConnection(jdbcUrl, username, password);

        } catch (ClassNotFoundException e) {
            ConnectionExceptions.handleDriverNotFound(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            ConnectionExceptions.handleConnectionCloseError(e);
        }
    }
}

