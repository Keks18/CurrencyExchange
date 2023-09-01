package com.project.project_3_currencyexchange.services;

import com.project.project_3_currencyexchange.entities.Currency;

import java.sql.SQLException;
import java.util.List;

public interface CurrencyService {
    public List<Currency> getAll() throws SQLException;
    public Currency getById(Integer id);
    Currency findByCode(String code) throws SQLException;
}
