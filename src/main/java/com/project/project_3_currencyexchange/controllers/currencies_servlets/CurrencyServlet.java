package com.project.project_3_currencyexchange.controllers.currencies_servlets;

import com.project.project_3_currencyexchange.entities.Currency;
import com.project.project_3_currencyexchange.exceptions.ServletExceptions;
import com.project.project_3_currencyexchange.services.CurrencyService;
import com.project.project_3_currencyexchange.services.CurrencyServiceImpl;
import com.project.project_3_currencyexchange.utils.JsonTransformer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "currencyServlet", value = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    CurrencyService currencyService = new CurrencyServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String code = pathInfo.substring(1);
            PrintWriter writer = resp.getWriter();
            try {
                Currency currency = currencyService.findByCode(code);
                if (!currency.isEmpty()){
                    writer.println(JsonTransformer.transformToJson(currency));
                }
                else {
                    ServletExceptions.currencyNotFound(resp);
                }
            } catch (SQLException e) {
                ServletExceptions.databaseOperationFail(resp, e);
            }
        } else {
            ServletExceptions.emptyCurrencyCode(resp);
        }
    }


}
