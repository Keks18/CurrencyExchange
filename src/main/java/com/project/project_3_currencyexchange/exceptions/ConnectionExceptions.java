package com.project.project_3_currencyexchange.exceptions;


import java.io.IOException;
import java.sql.SQLException;

public class ConnectionExceptions {
    public static void handlePropertiesLoadingFailure(IOException e) {
        System.out.println("Не получилось загрузить db.properties " + e);
    }
    public static void handleDriverNotFound(ClassNotFoundException e) {
        System.out.println("Не удалось загрузить MySQL драйвер из db.properties " + e);
    }
    public static void handleConnectionCloseError(SQLException e) {
        System.out.println("Ошибка при закрытии соединения " + e);
    }
}
