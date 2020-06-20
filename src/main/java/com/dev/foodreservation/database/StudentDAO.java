package com.dev.foodreservation.database;

import com.dev.foodreservation.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentDAO implements DAO<Student>{

    private Connection databaseConnection = null;

    public StudentDAO() throws SQLException {
        databaseConnection = new DatabaseConnection().connect();
    }

    @Override
    public Optional<Student> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Student> getAll() {
        return null;
    }

    @Override
    public boolean save(Student student) {
        try{
            return databaseConnection.createStatement().executeUpdate(
                    "EXEC [dbo].[SaveStudent]" +
                            "@ri = "+student.getRollId()+", " +
                            "@i = "+student.getId()+", " +
                            "@fn = "+student.getFirstName()+", " +
                            "@ln = "+student.getLastName()+", " +
                            "@s = "+student.getGender()+", " +
                            "@ml = "+student.getMealLimit()+""
            ) > 0;
        } catch (SQLException exception){
            return false;
        }
    }

    @Override
    public boolean update(Student student, Student tt) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
