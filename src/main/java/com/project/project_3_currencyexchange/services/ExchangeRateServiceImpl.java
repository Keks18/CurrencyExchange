package com.project.project_3_currencyexchange.services;

import com.project.project_3_currencyexchange.dao.ExchangeRateDAO;
import com.project.project_3_currencyexchange.dao.ExchangeRateDAOJdbc;
import com.project.project_3_currencyexchange.entities.ExchangeRate;

import java.sql.SQLException;
import java.util.List;

public class ExchangeRateServiceImpl implements ExchangeRateService{
    ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAOJdbc();
    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        return exchangeRateDAO.findAll();
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
        return exchangeRateDAO.save(exchangeRate);
    }

    @Override
    public ExchangeRate findByCode(String code1, String code2) throws SQLException {
        return exchangeRateDAO.findByCode(code1, code2);
    }
}
