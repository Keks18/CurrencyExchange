package com.project.project_3_currencyexchange.repository;

import java.sql.*;

public class JdbcConnection {
    Connection connection;
    public void startDb() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/currencyexchange?useSSL=false&serverTimezone=UTC";
        String username = "bestuser";
        String password = "bestuser";

        try {
            // Загружаем драйвер MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Соединение установлено!");

        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер MySQL не найден!");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void endDb() throws SQLException {
        System.out.println("Соединение завершено!");
        connection.close();
    }
}

