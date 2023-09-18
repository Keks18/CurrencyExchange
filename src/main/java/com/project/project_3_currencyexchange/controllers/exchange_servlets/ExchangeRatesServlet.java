package com.project.project_3_currencyexchange.controllers.exchange_servlets;

import com.project.project_3_currencyexchange.entities.Currency;
import com.project.project_3_currencyexchange.entities.ExchangeRate;
import com.project.project_3_currencyexchange.exceptions.ExchangeRateServletExceptions;
import com.project.project_3_currencyexchange.exceptions.ServletExceptions;
import com.project.project_3_currencyexchange.services.ExchangeRateService;
import com.project.project_3_currencyexchange.services.ExchangeRateServiceImpl;
import com.project.project_3_currencyexchange.utils.JsonTransformer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@WebServlet(name = "exchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    static final ExchangeRateService exchangeRateService = new ExchangeRateServiceImpl();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        List<ExchangeRate> exchangeRates;
        try {
            exchangeRates = exchangeRateService.findAll();
        } catch (SQLException e) {
            ServletExceptions.databaseOperationFail(resp, e);
            return;
        }

        writer.println(JsonTransformer.transformToJson(exchangeRates));
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        ExchangeRate exchangeReq = new ExchangeRate();
        ExchangeRate exchangeResp;

        exchangeReq.setBaseCurrencyId(new Currency());
        exchangeReq.getBaseCurrencyId().setCode((req.getParameter("baseCurrencyCode")));

        exchangeReq.setTargetCurrencyId(new Currency());
        exchangeReq.getTargetCurrencyId().setCode((req.getParameter("targetCurrencyCode")));

        exchangeReq.setRate(new BigDecimal(req.getParameter("rate")));

        if (
                (exchangeReq.getBaseCurrencyId().getCode() == null) ||
                        (exchangeReq.getTargetCurrencyId().getCode() == null) ||
                        (exchangeReq.getRate() == null)
        ) {
            ServletExceptions.someFieldIsEmpty(resp);
            return;
        }

        try {
            exchangeResp = exchangeRateService.save(exchangeReq);
        } catch (SQLIntegrityConstraintViolationException e) {
            ExchangeRateServletExceptions.duplicatedExchangeRate(resp);
            return;
        } catch (SQLException e) {
            ServletExceptions.databaseOperationFail(resp, e);
            return;
        }

        writer.println(JsonTransformer.transformToJson(exchangeResp));
        writer.close();
    }
}
