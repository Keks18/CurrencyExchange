package com.project.project_3_currencyexchange.dao;

import com.project.project_3_currencyexchange.entities.Currency;
import com.project.project_3_currencyexchange.repository.CrudRepository;

import java.sql.SQLException;

public interface CurrencyDAO extends CrudRepository<Currency> {
    Currency findByCode(String code) throws SQLException;
}
