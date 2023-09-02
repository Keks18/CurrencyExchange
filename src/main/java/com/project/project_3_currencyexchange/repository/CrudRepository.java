package com.project.project_3_currencyexchange.repository;

import java.util.List;
import java.sql.SQLException;

public interface CrudRepository<T> {
    List<T> findAll() throws SQLException;
    T findById(Integer id) throws SQLException;
    void update(T t) throws SQLException;
    T save(T t) throws SQLException;
}
