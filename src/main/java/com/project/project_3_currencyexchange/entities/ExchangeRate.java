package com.project.project_3_currencyexchange.entities;

import java.math.BigDecimal;

public class ExchangeRate {
    private Integer id;
    private Currency baseCurrencyId;
    private Currency targetCurrencyId;
    private BigDecimal rate;

    public ExchangeRate() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Currency getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public void setBaseCurrencyId(Currency baseCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
    }

    public Currency getTargetCurrencyId() {
        return targetCurrencyId;
    }

    public void setTargetCurrencyId(Currency targetCurrencyId) {
        this.targetCurrencyId = targetCurrencyId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public boolean isEmpty() {
        return id == null &&
                baseCurrencyId == null &&
                targetCurrencyId == null &&
                rate == null;
    }
}
