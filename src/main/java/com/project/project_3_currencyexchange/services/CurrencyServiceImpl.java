package com.project.project_3_currencyexchange.services;

import com.project.project_3_currencyexchange.dao.CurrencyDAO;
import com.project.project_3_currencyexchange.dao.CurrencyDAOJdbc;
import com.project.project_3_currencyexchange.entities.Currency;

import java.sql.SQLException;
import java.util.List;

public class CurrencyServiceImpl implements CurrencyService{
    CurrencyDAO currencyDAO = new CurrencyDAOJdbc();

    public CurrencyServiceImpl() {
    }

    @Override
    public List<Currency> getAll() throws SQLException {
            return currencyDAO.findAll();
    }

    @Override
    public Currency getById(Integer id) {
        return new com.project.project_3_currencyexchange.entities.Currency(id, "USD", "US Dollar", "$");
    }

    @Override
    public Currency findByCode(String code) throws SQLException {
            return currencyDAO.findByCode(code);
    }
}
