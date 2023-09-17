package com.project.project_3_currencyexchange.services;

import com.project.project_3_currencyexchange.repository.ExchangeRateRepository;
import com.project.project_3_currencyexchange.repository.ExchangeRateRepositoryJdbc;
import com.project.project_3_currencyexchange.dto.ExchangeDTO;
import com.project.project_3_currencyexchange.entities.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

public class ExchangeRateServiceImpl implements ExchangeRateService {
    ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepositoryJdbc();
    CurrencyService currencyService = new CurrencyServiceImpl();

    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        return exchangeRateRepository.findAll();
    }

    @Override
    public ExchangeRate findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public ExchangeRate update(BigDecimal rate, String code1, String code2) throws SQLException {
        return exchangeRateRepository.update(rate, code1, code2);
    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) throws SQLException {
        return exchangeRateRepository.save(exchangeRate);
    }

    @Override
    public ExchangeRate findByCode(String code1, String code2) throws SQLException {
        return exchangeRateRepository.findByCode(code1, code2);
    }

    @Override
    public ExchangeRate exchangeCurrency(String from, String to, BigDecimal amount) throws SQLException {
        ExchangeDTO exchangeDTO = new ExchangeDTO();
        exchangeDTO.setAmount(amount);
        exchangeDTO.setBaseCurrencyId(currencyService.findByCode(from));
        exchangeDTO.setTargetCurrencyId(currencyService.findByCode(to));

        BigDecimal rateFromTo = findByCode(from, to).getRate();
        if (rateFromTo != null) {
            exchangeDTO.setRate(rateFromTo);
            exchangeDTO.exchangeCurrency();
            return exchangeDTO;
        }

        BigDecimal rateToFrom = findByCode(to, from).getRate();
        if (rateToFrom != null) {
            rateFromTo = BigDecimal.ONE.divide(rateToFrom, 6, RoundingMode.HALF_UP);
            exchangeDTO.setRate(rateFromTo);
            exchangeDTO.exchangeCurrency();
            return exchangeDTO;
        }

        BigDecimal rateUsdFrom = findByCode("USD", from).getRate();
        BigDecimal rateUsdTo = findByCode("USD", to).getRate();
        if (
                (rateUsdFrom != null)
                        &&
                        (rateUsdTo) != null) {
            BigDecimal rate = rateUsdTo.divide(rateUsdFrom, 6, RoundingMode.HALF_UP);
            exchangeDTO.setRate(rate);
        }
        exchangeDTO.exchangeCurrency();
        return exchangeDTO;
    }
}
