package com.project.project_3_currencyexchange.dao;

import com.project.project_3_currencyexchange.entities.Currency;
import com.project.project_3_currencyexchange.repository.JdbcConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        try (Statement statement = jdbcConnection.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
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
    public void update(Currency currency) throws SQLException {

    }

    @Override
    public void save(Currency currency) throws SQLException {

    }
    @Override
    public Currency findByCode(String code) throws SQLException {
        Currency currency = new Currency();
        jdbcConnection.startDb();
        String sql = "SELECT * FROM currencyexchange.currencies WHERE Code = ?";
        try (PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql);
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
