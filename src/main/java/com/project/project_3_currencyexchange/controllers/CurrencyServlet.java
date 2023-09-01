package com.project.project_3_currencyexchange.controllers;

import com.project.project_3_currencyexchange.services.CurrencyService;
import com.project.project_3_currencyexchange.services.CurrencyServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "currencyServlet", value = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    CurrencyService currencyService = new CurrencyServiceImpl();
    String code;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String code = pathInfo.substring(1);
            PrintWriter writer = resp.getWriter();
            writer.println(currencyService.findByCode(code));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
