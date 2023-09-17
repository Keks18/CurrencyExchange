package com.project.project_3_currencyexchange.controllers.exchange_servlets;

import com.project.project_3_currencyexchange.dto.ExchangeDTO;
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
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(name = "exchangeServlet", value = "/exchange")
public class ExchangeServlet extends HttpServlet {
    ExchangeRateService exchangeRateService = new ExchangeRateServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        BigDecimal amount = new BigDecimal(req.getParameter("amount"));
        ExchangeRate exchangeDTO = null;

        try {
            exchangeDTO = exchangeRateService.exchangeCurrency(from, to, amount);
        } catch (SQLException e) {
            ServletExceptions.databaseOperationFail(resp, e);
        }
        writer.println(JsonTransformer.transformToJson(exchangeDTO));
        writer.close();
    }
}
