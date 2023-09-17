package com.project.project_3_currencyexchange.exceptions;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExchangeRateServletExceptions {
    public static void exchangeRateNotFound(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Обменный курс для пары не найден ");
    }

    public static void duplicatedExchangeRate(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_CONFLICT);
        resp.sendError(HttpServletResponse.SC_CONFLICT, "Валютная пара с таким кодом уже существует ");
    }
}
