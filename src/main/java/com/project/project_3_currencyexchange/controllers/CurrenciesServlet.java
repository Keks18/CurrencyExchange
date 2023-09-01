package com.project.project_3_currencyexchange.controllers;

import com.project.project_3_currencyexchange.entities.Currency;
import com.project.project_3_currencyexchange.exceptions.ServletExceptions;
import com.project.project_3_currencyexchange.services.CurrencyService;
import com.project.project_3_currencyexchange.services.CurrencyServiceImpl;
import com.project.project_3_currencyexchange.utils.JsonTransformer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "currenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    CurrencyService currencyService = new CurrencyServiceImpl();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        List<Currency> currencies;
        try {
            currencies = currencyService.findAll();
        } catch (SQLException e) {
            ServletExceptions.databaseOperationFail(resp, e);
            return;
        }

        if(currencies.isEmpty()){
            ServletExceptions.listOfCurrenciesIsEmpty(resp);
            return;
        }

        writer.println(JsonTransformer.transformToJson(currencies));
    }
}
