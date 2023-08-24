package com.project.project_3_currencyexchange.controllers;

import com.project.project_3_currencyexchange.services.CurrencyService;
import com.project.project_3_currencyexchange.services.CurrencyServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "currenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    CurrencyService currencyService;

    @Override
    public void init() {
        currencyService = new CurrencyServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        writer.println(currencyService.getById(1));


    }
}
