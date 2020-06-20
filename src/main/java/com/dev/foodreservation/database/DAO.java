package com.dev.foodreservation.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(long id);

    List<T> getAll();

    boolean save(T t) throws SQLException;
    boolean update(T t, T tt);
    boolean delete(long id);
}
