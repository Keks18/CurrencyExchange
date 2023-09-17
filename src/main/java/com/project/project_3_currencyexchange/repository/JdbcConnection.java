package com.project.project_3_currencyexchange.repository;

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
            throw new RuntimeException(e);
        }

        String jdbcUrl = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        String driverName = properties.getProperty("db.driverName");
        try {
            // Загружаем драйвер MySQL
            Class.forName(driverName);

            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Соединение установлено!");

        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер MySQL не найден!");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        try {
            this.connection.close();
            System.out.println("Соединение завершено!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

