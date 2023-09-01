package com.project.project_3_currencyexchange.controllers;

import com.project.project_3_currencyexchange.entities.Currency;
import com.project.project_3_currencyexchange.services.CurrencyService;
import com.project.project_3_currencyexchange.services.CurrencyServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "currenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    CurrencyService currencyService = new CurrencyServiceImpl();

    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        List<Currency> currencies = currencyService.getAll();
        PrintWriter writer = resp.getWriter();

        if(currencies.isEmpty()){
            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Сбой в работе с базой данных");
        }
        currencies.stream().forEach(e -> writer.println(e));


    }
}
