package com.project.project_3_currencyexchange.repository;

import java.math.BigDecimal;
import java.util.List;
import java.sql.SQLException;

public interface CrudRepository<T> {
    List<T> findAll() throws SQLException;

    T findById(Integer id) throws SQLException;

    T update(BigDecimal rate, String code1, String code2) throws SQLException;

    T save(T t) throws SQLException;
}
