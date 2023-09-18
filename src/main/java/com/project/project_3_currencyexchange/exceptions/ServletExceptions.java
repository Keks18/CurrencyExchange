package com.project.project_3_currencyexchange.exceptions;

import com.google.gson.JsonObject;
import com.project.project_3_currencyexchange.utils.JsonTransformer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletExceptions {
    public static void databaseOperationFail(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Сбой в работе с базой данных " + e.getMessage());
        resp.getWriter().write(JsonTransformer.transformToJson(jsonObject));
    }

    public static void emptyCurrencyCode(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Код валюты отсутствует в адресе");
        resp.getWriter().write(JsonTransformer.transformToJson(jsonObject));
    }

    public static void currencyNotFound(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Валюта не найдена");
        resp.getWriter().write(JsonTransformer.transformToJson(jsonObject));
    }

    public static void listOfCurrenciesIsEmpty(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Список валют пуст");
        resp.getWriter().write(JsonTransformer.transformToJson(jsonObject));
    }

    public static void someFieldIsEmpty(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Отсутствует нужное поле формы");
        resp.getWriter().write(JsonTransformer.transformToJson(jsonObject));
    }

    public static void duplicatedCurrency(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_CONFLICT);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Валюта с таким кодом уже существует");
        resp.getWriter().write(JsonTransformer.transformToJson(jsonObject));
    }

}
