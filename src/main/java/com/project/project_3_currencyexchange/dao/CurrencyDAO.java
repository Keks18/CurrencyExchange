package com.project.project_3_currencyexchange.dao;

import com.project.project_3_currencyexchange.entities.Currency;
import java.util.List;

public interface CurrencyDAO {
    public List<Currency> getAllCurrencies();
    public Currency getCurrencyById(Integer id);
    public void saveOrUpdateCurrency(Currency currency);
}
