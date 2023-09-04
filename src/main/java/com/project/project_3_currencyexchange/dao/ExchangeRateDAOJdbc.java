package com.project.project_3_currencyexchange.dao;

import com.project.project_3_currencyexchange.entities.Currency;
import com.project.project_3_currencyexchange.entities.ExchangeRate;
import com.project.project_3_currencyexchange.repository.JdbcConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAOJdbc implements ExchangeRateDAO{
    JdbcConnection jdbcConnection = new JdbcConnection();
    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        jdbcConnection.startDb();
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

        try (PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql);
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
        } finally {
            jdbcConnection.endDb();
        }
        return exchangeRates;
    }

    @Override
    public ExchangeRate findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public void update(ExchangeRate exchangeRate) throws SQLException {

    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) throws SQLException {
        jdbcConnection.startDb();
        Currency currencyBase = exchangeRate.getBaseCurrencyId();
        Currency currencyTarget = exchangeRate.getTargetCurrencyId();

        currencyBase.setId(getIdFromDatabase(currencyBase));
        currencyTarget.setId(getIdFromDatabase(currencyTarget));

        String sql = "INSERT INTO currencyexchange.exchangerates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (?, ?, ?);";
        try (PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql)
        ) {
            statement.setInt(1, exchangeRate.getBaseCurrencyId().getId());
            statement.setInt(2, exchangeRate.getTargetCurrencyId().getId());
            statement.setBigDecimal(3, exchangeRate.getRate());
            int rowsAffected  = statement.executeUpdate();

            if (rowsAffected  != 1){
                throw new SQLIntegrityConstraintViolationException("Ошибка нарушения ограничения целостности данных");
            }
        } finally {
            jdbcConnection.endDb();
        }
        return findByCode(exchangeRate.getBaseCurrencyId().getCode(), exchangeRate.getTargetCurrencyId().getCode());
    }
    @Override
    public ExchangeRate findByCode(String code1, String code2) throws SQLException {
        ExchangeRate exchangeRate = new ExchangeRate();
        jdbcConnection.startDb();
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

        try (PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql);
             ) {
            statement.setString(1, code1);
            statement.setString(2, code2);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
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
            }
        } finally {
            jdbcConnection.endDb();
        }
        return exchangeRate;
    }
    private int getIdFromDatabase(Currency currency) throws SQLException {
        String sql1 = "select ID from currencies where Code = ?;";
        try (PreparedStatement statement = jdbcConnection.getConnection().prepareStatement(sql1)
        ) {
            statement.setString(1, currency.getCode());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                currency.setId(resultSet.getInt("id"));
            }
        }
        return currency.getId();
    }
}
