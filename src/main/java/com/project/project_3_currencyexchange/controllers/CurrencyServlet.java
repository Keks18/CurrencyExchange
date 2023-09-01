package com.project.project_3_currencyexchange.controllers;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Currency currency = new Currency();
        currency.setSign(req.getParameter("code"));
        currency.setCode(req.getParameter("sign"));
        currency.setFullName(req.getParameter("name"));
        if (currency.isEmpty()){
            //TODO do normal exception to this situation
            ServletExceptions.emptyCurrencyCode(resp);
        }
        try {
            currencyService.save(currency);
            System.out.println(currency);
        } catch (SQLException e) {
            //TODO тут поменять как в тз ошибка 409 создать новый ексепш
            ServletExceptions.databaseOperationFail(resp, e);
        }
        PrintWriter writer = resp.getWriter();
        writer.println(JsonTransformer.transformToJson(currency));
    }
}
