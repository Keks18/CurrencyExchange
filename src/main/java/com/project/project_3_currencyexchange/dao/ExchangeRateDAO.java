package com.project.project_3_currencyexchange.dao;

import com.project.project_3_currencyexchange.entities.ExchangeRate;
import com.project.project_3_currencyexchange.repository.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface ExchangeRateDAO extends CrudRepository<ExchangeRate> {
    ExchangeRate findByCode(String code1, String code2) throws SQLException;
}
