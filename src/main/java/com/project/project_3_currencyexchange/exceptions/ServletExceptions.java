package com.project.project_3_currencyexchange.exceptions;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletExceptions {
    public static void databaseOperationFail(HttpServletResponse resp, Exception e) throws IOException {
        resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Сбой в работе с базой данных " + e.getMessage());
    }
    public static void emptyCurrencyCode(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Код валюты отсутствует в адресе ");
    }
    public static void currencyNotFound(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Валюта не найдена ");
    }
    public static void listOfCurrenciesIsEmpty(HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Список валют пуст ");

    }
    public static void someFieldIsEmpty(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствует нужное поле формы ");
    }
    public static void duplicatedCurrency(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_CONFLICT);
        resp.sendError(HttpServletResponse.SC_CONFLICT, "Валюта с таким кодом уже существует ");
    }

}
