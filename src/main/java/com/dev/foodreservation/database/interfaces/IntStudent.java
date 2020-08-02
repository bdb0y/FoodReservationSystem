package com.dev.foodreservation.database.interfaces;

import com.dev.foodreservation.objects.Student;

import java.sql.SQLException;
import java.util.List;

public interface IntStudent {

    List<Student> rollIdGet(long id) throws SQLException;
    List<Student> IdGet(long id) throws SQLException;

    List<Student> getAll(int flag) throws SQLException;

    boolean save(Student t) throws SQLException;
    boolean updateNameSex(Student student) throws SQLException;
    boolean updateStudentMealLimit(long id, byte limit) throws SQLException;
    boolean delete(long id) throws SQLException;
}