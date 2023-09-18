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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@WebServlet(name = "currenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    static final CurrencyService currencyService = new CurrencyServiceImpl();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        writer.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Currency currencyReq = new Currency();
        Currency currencyResp;

        currencyReq.setSign(req.getParameter("sign"));
        currencyReq.setCode(req.getParameter("code"));
        currencyReq.setFullName(req.getParameter("name"));

        // Check if any of the fields (code, sign, fullName) in the 'currency' object is null or empty.
        // If any field is null or empty, throw an exception using 'ServletExceptions.someFieldIsEmpty(resp)'.
        if (
                (currencyReq.getCode() == null || currencyReq.getCode().isEmpty()) ||
                (currencyReq.getSign() == null || currencyReq.getSign().isEmpty()) ||
                (currencyReq.getFullName() == null || currencyReq.getFullName().isEmpty())
            )
        {
            ServletExceptions.someFieldIsEmpty(resp);
            return;
        }

        try {
            currencyResp = currencyService.save(currencyReq);
        } catch (SQLIntegrityConstraintViolationException e) {
            ServletExceptions.duplicatedCurrency(resp);
            return;
        } catch (SQLException e) {
            ServletExceptions.databaseOperationFail(resp, e);
            return;
        }

        writer.println(JsonTransformer.transformToJson(currencyResp));
        writer.close();
    }
}
