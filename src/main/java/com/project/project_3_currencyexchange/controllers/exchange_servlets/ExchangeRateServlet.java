package com.project.project_3_currencyexchange.controllers.exchange_servlets;

import com.project.project_3_currencyexchange.entities.ExchangeRate;
import com.project.project_3_currencyexchange.exceptions.ExchangeRateServletExceptions;
import com.project.project_3_currencyexchange.exceptions.ServletExceptions;
import com.project.project_3_currencyexchange.services.ExchangeRateService;
import com.project.project_3_currencyexchange.services.ExchangeRateServiceImpl;
import com.project.project_3_currencyexchange.utils.JsonTransformer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "exchangeRateServlet", value = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    ExchangeRateService exchangeRateService = new ExchangeRateServiceImpl();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        ExchangeRate exchangeRate = new ExchangeRate();
        String pathInfo = req.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1){
            String currencyCode1 = pathInfo.substring(1, 4);
            String currencyCode2 = pathInfo.substring(4);
            try {
                exchangeRate = exchangeRateService.findByCode(currencyCode1, currencyCode2);
                if (exchangeRate.isEmpty()){
                    ExchangeRateServletExceptions.exchangeRateNotFound(resp);
                }
            } catch (SQLException e) {
                ServletExceptions.databaseOperationFail(resp, e);
            }
        } else {
            ServletExceptions.emptyCurrencyCode(resp);
        }

        writer.println(JsonTransformer.transformToJson(exchangeRate));
    }
}
