package com.project.project_3_currencyexchange.controllers.exchange_servlets;

import com.project.project_3_currencyexchange.entities.ExchangeRate;
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
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
@WebServlet(name = "exchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    ExchangeRateService exchangeRateService = new ExchangeRateServiceImpl();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        List<ExchangeRate> exchangeRates;
        try {
            exchangeRates = exchangeRateService.findAll();
        } catch (SQLException e) {
            ServletExceptions.databaseOperationFail(resp, e);
            return;
        }

        writer.println(JsonTransformer.transformToJson(exchangeRates));
    }
}
