package com.project.project_3_currencyexchange.services;

import com.project.project_3_currencyexchange.entities.Currency;
import com.project.project_3_currencyexchange.repository.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface CurrencyService extends CrudRepository<Currency> {
    Currency findByCode(String code) throws SQLException;
}
