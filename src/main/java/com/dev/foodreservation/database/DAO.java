package com.dev.foodreservation.database;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    List<T> rollIdGet(long id) throws SQLException;
    List<T> IdGet(long id) throws SQLException;

    List<T> getAll() throws SQLException;

    boolean save(T t) throws SQLException;
    boolean updateNameSex(long id,
                       String firstName, String lastName,
                       byte sex) throws SQLException;
    boolean updateStudentMealLimit(long id, byte limit) throws SQLException;
    boolean delete(long id) throws SQLException;
}
