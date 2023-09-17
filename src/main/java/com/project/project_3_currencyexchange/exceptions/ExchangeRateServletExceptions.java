package com.project.project_3_currencyexchange.exceptions;

import com.google.gson.JsonObject;
import com.project.project_3_currencyexchange.utils.JsonTransformer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExchangeRateServletExceptions {
    public static void exchangeRateNotFound(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Обменный курс для пары не найден");
        resp.getWriter().write(JsonTransformer.transformToJson(jsonObject));
    }

    public static void duplicatedExchangeRate(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_CONFLICT);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Валютная пара с таким кодом уже существует");
        resp.getWriter().write(JsonTransformer.transformToJson(jsonObject));
    }
}
