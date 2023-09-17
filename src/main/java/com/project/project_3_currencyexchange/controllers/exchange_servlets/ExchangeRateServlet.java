package com.project.project_3_currencyexchange.controllers.exchange_servlets;

import com.project.project_3_currencyexchange.controllers.PatcherServlet;
import com.project.project_3_currencyexchange.entities.ExchangeRate;
import com.project.project_3_currencyexchange.exceptions.ExchangeRateServletExceptions;
import com.project.project_3_currencyexchange.exceptions.ServletExceptions;
import com.project.project_3_currencyexchange.services.ExchangeRateService;
import com.project.project_3_currencyexchange.services.ExchangeRateServiceImpl;
import com.project.project_3_currencyexchange.utils.JsonTransformer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

@WebServlet(name = "exchangeRateServlet", value = "/exchangeRate/*")
public class ExchangeRateServlet extends PatcherServlet {
    ExchangeRateService exchangeRateService = new ExchangeRateServiceImpl();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        ExchangeRate exchangeRate = new ExchangeRate();
        String pathInfo = req.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
            String currencyCode1 = pathInfo.substring(1, 4);
            String currencyCode2 = pathInfo.substring(4);
            try {
                exchangeRate = exchangeRateService.findByCode(currencyCode1, currencyCode2);
                if (exchangeRate.isEmpty()) {
                    ExchangeRateServletExceptions.exchangeRateNotFound(resp);
                }
            } catch (SQLException e) {
                ServletExceptions.databaseOperationFail(resp, e);
            }
        } else {
            ServletExceptions.emptyCurrencyCode(resp);
        }

        writer.println(JsonTransformer.transformToJson(exchangeRate));
        writer.close();
    }

    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRate exchangeRate = new ExchangeRate();
        PrintWriter writer = resp.getWriter();
        String pathInfo = req.getPathInfo();
        String code1 = pathInfo.substring(1, 4);
        String code2 = pathInfo.substring(4);
        BigDecimal rate = null;
        String rateParam = req.getParameter("rate");
        if (rateParam != null && !rateParam.isEmpty()) {
            rate = new BigDecimal(rateParam);
        }
        if (rate == null) {
            ServletExceptions.someFieldIsEmpty(resp);
            return;
        }
        try {
            exchangeRate = exchangeRateService.update(rate, code1, code2);
        } catch (SQLSyntaxErrorException e) {
            ExchangeRateServletExceptions.exchangeRateNotFound(resp);
        } catch (SQLException e) {
            ServletExceptions.databaseOperationFail(resp, e);
        }
        writer.println(JsonTransformer.transformToJson(exchangeRate));
        writer.close();
    }
}
