package com.project.project_3_currencyexchange.services;

import com.project.project_3_currencyexchange.dao.ExchangeRateDAO;
import com.project.project_3_currencyexchange.dao.ExchangeRateDAOJdbc;
import com.project.project_3_currencyexchange.dto.ExchangeDTO;
import com.project.project_3_currencyexchange.entities.ExchangeRate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ExchangeRateServiceImpl implements ExchangeRateService{
    ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAOJdbc();
    CurrencyService currencyService = new CurrencyServiceImpl();
    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        return exchangeRateDAO.findAll();
    }

    @Override
    public ExchangeRate findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public ExchangeRate update(BigDecimal rate, String code1, String code2) throws SQLException {
        return exchangeRateDAO.update(rate,code1,code2);
    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) throws SQLException {
        return exchangeRateDAO.save(exchangeRate);
    }

    @Override
    public ExchangeRate findByCode(String code1, String code2) throws SQLException {
        return exchangeRateDAO.findByCode(code1, code2);
    }

    @Override
    public ExchangeRate exchangeCurrency(String from, String to, BigDecimal amount) throws SQLException {
        ExchangeDTO exchangeDTO = new ExchangeDTO();
        exchangeDTO.setAmount(amount);
        exchangeDTO.setBaseCurrencyId(currencyService.findByCode(from));
        exchangeDTO.setTargetCurrencyId(currencyService.findByCode(to));
        exchangeDTO.setRate(findByCode(from, to).getRate());
        exchangeDTO.exchangeCurrency();
        return exchangeDTO;
    }
}
