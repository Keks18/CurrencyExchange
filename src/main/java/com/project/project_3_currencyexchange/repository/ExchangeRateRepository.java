package com.project.project_3_currencyexchange.repository;

import com.project.project_3_currencyexchange.entities.ExchangeRate;

import java.sql.SQLException;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate> {
    ExchangeRate findByCode(String code1, String code2) throws SQLException;
}
