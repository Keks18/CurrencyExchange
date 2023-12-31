package com.project.project_3_currencyexchange.dto;

import com.project.project_3_currencyexchange.entities.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeDTO extends ExchangeRate {
    private BigDecimal amount;
    private BigDecimal convertedAmount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public void exchangeCurrency() {
        this.convertedAmount = getAmount().multiply(getRate()).setScale(2, RoundingMode.HALF_UP);
    }
}
