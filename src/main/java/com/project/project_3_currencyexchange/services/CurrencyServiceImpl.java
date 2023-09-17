package com.project.project_3_currencyexchange.services;

import com.project.project_3_currencyexchange.repository.CurrencyRepository;
import com.project.project_3_currencyexchange.repository.CurrencyRepositoryJdbc;
import com.project.project_3_currencyexchange.entities.Currency;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class CurrencyServiceImpl implements CurrencyService {
    CurrencyRepository currencyRepository = new CurrencyRepositoryJdbc();

    @Override
    public List<Currency> findAll() throws SQLException {
        return currencyRepository.findAll();
    }

    @Override
    public Currency findByCode(String code) throws SQLException {
        return currencyRepository.findByCode(code);
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
        return currencyRepository.save(currency);
    }
}
