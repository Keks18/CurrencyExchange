package com.project.project_3_currencyexchange.dao;

import com.project.project_3_currencyexchange.entities.ExchangeRate;

import java.util.List;

public interface ExchangeRateDAO {
    public List<ExchangeRate> getAllExchangeRates();
    public ExchangeRate getExchangeRateById(Integer id);
    public void saveOrUpdateExchangeRate(ExchangeRate exchangeRate);
}
