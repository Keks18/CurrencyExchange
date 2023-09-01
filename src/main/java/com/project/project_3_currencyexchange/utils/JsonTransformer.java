package com.project.project_3_currencyexchange.utils;

import com.google.gson.Gson;

import java.util.List;

public class JsonTransformer {
    private static final Gson gson = new Gson();

    public static <T> String transformToJson(List<T> t){
        return gson.toJson(t);
    }

    public static <T> String transformToJson(T t){
        return gson.toJson(t);
    }
}
