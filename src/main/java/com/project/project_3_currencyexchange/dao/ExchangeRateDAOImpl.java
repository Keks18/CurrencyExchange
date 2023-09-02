package com.project.project_3_currencyexchange.dao;

import com.project.project_3_currencyexchange.entities.ExchangeRate;

import java.sql.SQLException;
import java.util.List;

public class ExchangeRateDAOImpl implements ExchangeRateDAO{

    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        return null;
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
        return exchangeRate;
    }
}
