package com.project.project_3_currencyexchange.services;

import com.project.project_3_currencyexchange.entities.Currency;
import java.util.List;

public class CurrencyServiceImpl implements CurrencyService{
    @Override
    public List<Currency> getAll() {
        return null;
    }

    @Override
    public Currency getById(Integer id) {
        return new com.project.project_3_currencyexchange.entities.Currency(id, "USD", "US Dollar", "$");
    }
}
