package com.project.project_3_currencyexchange.services;

import com.project.project_3_currencyexchange.entities.Currency;
import com.project.project_3_currencyexchange.entities.ExchangeRate;
import com.project.project_3_currencyexchange.repository.CrudRepository;

import java.sql.SQLException;

public interface ExchangeRateService extends CrudRepository<ExchangeRate> {
    ExchangeRate findByCode(String code1, String code2) throws SQLException;
}
