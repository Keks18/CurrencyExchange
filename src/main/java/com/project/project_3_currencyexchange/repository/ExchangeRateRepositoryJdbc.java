package com.project.project_3_currencyexchange.repository;

import com.project.project_3_currencyexchange.entities.Currency;
import com.project.project_3_currencyexchange.entities.ExchangeRate;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateRepositoryJdbc implements ExchangeRateRepository {

    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        String sql = "SELECT" +
                "    e.ID," +
                "    b.ID AS BaseCurrencyId," +
                "    b.FullName AS BaseCurrencyFullName," +
                "    b.Code AS BaseCurrencyCode," +
                "    b.Sign AS BaseCurrencySign," +
                "    t.ID AS TargetCurrencyId," +
                "    t.FullName AS TargetCurrencyFullName," +
                "    t.Code AS TargetCurrencyCode," +
                "    t.Sign AS TargetCurrencySign," +
                "    e.Rate " +
                "FROM ExchangeRates e" +
                "         INNER JOIN Currencies b ON e.BaseCurrencyId = b.ID" +
                "         INNER JOIN Currencies t ON e.TargetCurrencyId = t.ID;";

        try (JdbcConnection jdbcConnection = new JdbcConnection();
             PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ExchangeRate exchangeRate = new ExchangeRate();

                exchangeRate.setId(resultSet.getInt("id"));

                Currency baseCurrency = new Currency();
                baseCurrency.setId(resultSet.getInt("BaseCurrencyId"));
                baseCurrency.setCode(resultSet.getString("BaseCurrencyCode"));
                baseCurrency.setSign(resultSet.getString("BaseCurrencySign"));
                baseCurrency.setFullName(resultSet.getString("BaseCurrencyFullName"));
                exchangeRate.setBaseCurrencyId(baseCurrency);

                Currency targetCurrency = new Currency();
                targetCurrency.setId(resultSet.getInt("TargetCurrencyId"));
                targetCurrency.setCode(resultSet.getString("TargetCurrencyCode"));
                targetCurrency.setSign(resultSet.getString("TargetCurrencySign"));
                targetCurrency.setFullName(resultSet.getString("TargetCurrencyFullName"));
                exchangeRate.setTargetCurrencyId(targetCurrency);

                exchangeRate.setRate(resultSet.getBigDecimal("Rate"));
                exchangeRates.add(exchangeRate);
            }
        }
        return exchangeRates;
    }

    @Override
    public ExchangeRate findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public ExchangeRate update(BigDecimal rate, String code1, String code2) throws SQLException {
        String sql = "UPDATE exchangerates as e " +
                "INNER JOIN Currencies b ON e.BaseCurrencyId = b.ID " +
                "INNER JOIN Currencies t ON e.TargetCurrencyId = t.ID " +
                "SET e.Rate = ? " +
                "WHERE b.Code = ? and t.Code = ?;";

        try (JdbcConnection jdbcConnection = new JdbcConnection();
             PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql);
        ) {
            statement.setBigDecimal(1, rate);
            statement.setString(2, code1);
            statement.setString(3, code2);
            int res = statement.executeUpdate();

            if (res != 1) {
                throw new SQLSyntaxErrorException();
            }

        }
        return findByCode(code1, code2);
    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) throws SQLException {
        Currency currencyBase = exchangeRate.getBaseCurrencyId();
        Currency currencyTarget = exchangeRate.getTargetCurrencyId();

        currencyBase.setId(getIdFromDatabase(currencyBase));
        currencyTarget.setId(getIdFromDatabase(currencyTarget));

        String sql = "INSERT INTO currencyexchange.exchangerates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (?, ?, ?);";
        try (JdbcConnection jdbcConnection = new JdbcConnection();
             PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql);
        ) {
            statement.setInt(1, exchangeRate.getBaseCurrencyId().getId());
            statement.setInt(2, exchangeRate.getTargetCurrencyId().getId());
            statement.setBigDecimal(3, exchangeRate.getRate());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected != 1) {
                throw new SQLIntegrityConstraintViolationException("Ошибка нарушения ограничения целостности данных");
            }
        }
        return findByCode(exchangeRate.getBaseCurrencyId().getCode(), exchangeRate.getTargetCurrencyId().getCode());
    }

    @Override
    public ExchangeRate findByCode(String code1, String code2) throws SQLException {
        ExchangeRate exchangeRate = new ExchangeRate();
        String sql = "SELECT" +
                "    e.ID," +
                "    b.ID AS BaseCurrencyId," +
                "    b.FullName AS BaseCurrencyFullName," +
                "    b.Code AS BaseCurrencyCode," +
                "    b.Sign AS BaseCurrencySign," +
                "    t.ID AS TargetCurrencyId," +
                "    t.FullName AS TargetCurrencyFullName," +
                "    t.Code AS TargetCurrencyCode," +
                "    t.Sign AS TargetCurrencySign," +
                "    e.Rate " +
                "FROM ExchangeRates e" +
                "         INNER JOIN Currencies b ON e.BaseCurrencyId = b.ID" +
                "         INNER JOIN Currencies t ON e.TargetCurrencyId = t.ID " +
                "WHERE b.Code = ? and t.Code = ?;";

        try (JdbcConnection jdbcConnection = new JdbcConnection();
             PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql);
        ) {
            statement.setString(1, code1);
            statement.setString(2, code2);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    Currency baseCurrency = new Currency();
                    baseCurrency.setId(resultSet.getInt("BaseCurrencyId"));
                    baseCurrency.setCode(resultSet.getString("BaseCurrencyCode"));
                    baseCurrency.setSign(resultSet.getString("BaseCurrencySign"));
                    baseCurrency.setFullName(resultSet.getString("BaseCurrencyFullName"));
                    exchangeRate.setBaseCurrencyId(baseCurrency);

                    Currency targetCurrency = new Currency();
                    targetCurrency.setId(resultSet.getInt("TargetCurrencyId"));
                    targetCurrency.setCode(resultSet.getString("TargetCurrencyCode"));
                    targetCurrency.setSign(resultSet.getString("TargetCurrencySign"));
                    targetCurrency.setFullName(resultSet.getString("TargetCurrencyFullName"));
                    exchangeRate.setTargetCurrencyId(targetCurrency);

                    exchangeRate.setId(resultSet.getInt("id"));
                    exchangeRate.setRate(resultSet.getBigDecimal("Rate"));
                }
            }
        }
        return exchangeRate;
    }

    private int getIdFromDatabase(Currency currency) throws SQLException {
        String sql = "select ID from currencies where Code = ?;";
        try (JdbcConnection jdbcConnection = new JdbcConnection();
             PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql);
        ) {
            statement.setString(1, currency.getCode());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    currency.setId(resultSet.getInt("id"));
                }
            }
        }
        return currency.getId();
    }
}
