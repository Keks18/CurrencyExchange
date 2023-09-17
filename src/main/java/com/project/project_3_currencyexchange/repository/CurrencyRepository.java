package com.project.project_3_currencyexchange.repository;

import com.project.project_3_currencyexchange.entities.Currency;

import java.sql.SQLException;

public interface CurrencyRepository extends CrudRepository<Currency> {
    Currency findByCode(String code) throws SQLException;
}
