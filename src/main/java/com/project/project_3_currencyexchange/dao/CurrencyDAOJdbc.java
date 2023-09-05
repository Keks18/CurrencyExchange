package com.project.project_3_currencyexchange.dao;

import com.project.project_3_currencyexchange.entities.Currency;
import com.project.project_3_currencyexchange.repository.JdbcConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAOJdbc implements CurrencyDAO {
    JdbcConnection jdbcConnection = new JdbcConnection();

    public CurrencyDAOJdbc() {
    }
    @Override
    public List<Currency> findAll() throws SQLException {
        List<Currency> currencies = new ArrayList<>();

        jdbcConnection.startDb();
        String sql = "SELECT * FROM currencyexchange.currencies;";
        try (PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Currency currency = new Currency();

                currency.setId(resultSet.getInt("id"));
                currency.setCode(resultSet.getString("Code"));
                currency.setFullName(resultSet.getString("FullName"));
                currency.setSign(resultSet.getString("Sign"));

                currencies.add(currency);
            }
        } finally {
            jdbcConnection.endDb();
        }
        return currencies;
    }

    @Override
    public Currency findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public Currency update(BigDecimal rate, String code1, String code2) throws SQLException {
        return null;
    }

    @Override
    public Currency save(Currency currency) throws SQLException {
        jdbcConnection.startDb();
        String sql = "INSERT INTO currencyexchange.currencies (Code, FullName, Sign) VALUES (?, ?, ?);";
        try (PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql)
        ) {
            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getFullName());
            statement.setString(3, currency.getSign());
            int rowsAffected  = statement.executeUpdate();

            if (rowsAffected  != 1){
                throw new SQLIntegrityConstraintViolationException("Ошибка нарушения ограничения целостности данных");
            }
        } finally {
            jdbcConnection.endDb();
        }
        return findByCode(currency.getCode());
    }
    @Override
    public Currency findByCode(String code) throws SQLException {
        Currency currency = new Currency();
        jdbcConnection.startDb();
        String sql = "SELECT * FROM currencyexchange.currencies WHERE Code = ?";
        try (PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql)
             ) {
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                currency.setId(resultSet.getInt("id"));
                currency.setCode(resultSet.getString("Code"));
                currency.setFullName(resultSet.getString("FullName"));
                currency.setSign(resultSet.getString("Sign"));
            }
            resultSet.close();
        } finally {
            jdbcConnection.endDb();
        }
        return currency;
    }

}
