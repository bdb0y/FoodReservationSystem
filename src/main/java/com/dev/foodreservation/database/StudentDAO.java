package com.dev.foodreservation.database;

import com.dev.foodreservation.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
                    "insert into MealAutomation.dbo.Student values('"
                            +student.getRollId()+ "', '"
                            +student.getId()+ "', '"
                            +student.getFirstName()+ "', '"
                            +student.getLastName()+ "', '"
                            +student.getGender()+ "', '"
                            +student.getMealLimit()+ "')"
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
