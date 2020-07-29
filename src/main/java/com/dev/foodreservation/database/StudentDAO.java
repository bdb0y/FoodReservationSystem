package com.dev.foodreservation.database;

import com.dev.foodreservation.database.interfaces.IntStudent;
import com.dev.foodreservation.database.utilities.Executor;
import com.dev.foodreservation.database.utilities.Procedure;
import com.dev.foodreservation.objects.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IntStudent {

    private final Connection connection;
    private final Statement statement;

    public StudentDAO() throws SQLException {
        connection = new DatabaseConnection().connect();
        statement = connection.createStatement();
    }

    @Override
    public List<Student> getAll() throws SQLException {
        Procedure procedure = new Procedure("getAllStudents");
        List<Student> students = new ArrayList<>();
        ResultSet set = new Executor(statement).Execute(procedure);
        while (set.next()) students.add(studentRSV(set));
        return students;
    }

    @Override
    public List<Student> rollIdGet(long id) throws SQLException {
        Procedure procedure = new Procedure("RollIdGetStudent");
        procedure.addField("ri", id);
        List<Student> students = new ArrayList<>();
        ResultSet set = new Executor(statement).Execute(procedure);
        while (set.next()) students.add(studentRSV(set));
        return students;
    }

    @Override
    public List<Student> IdGet(long id) throws SQLException {
        Procedure procedure = new Procedure("IdGetStudent");
        procedure.addField("id", id);
        List<Student> students = new ArrayList<>();
        ResultSet set = new Executor(statement).Execute(procedure);
        while (set.next()) students.add(studentRSV(set));
        return students;
    }

    @Override
    public boolean save(Student student) throws SQLException {
        Procedure procedure = new Procedure("SaveStudent");
        procedure.addField("ri", student.getRollId());
        procedure.addField("i", student.getId());
        procedure.addField("fn", student.getFirstName());
        procedure.addField("ln", student.getLastName());
        procedure.addField("s", student.getGender());
        procedure.addField("ml", student.getMealLimit());
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public boolean updateNameSex(long id, String firstName,
                                 String lastName, byte sex) throws SQLException {
        Procedure procedure = new Procedure("UpdateStudentFirstNameLastName");
        procedure.addField("ri", id);
        procedure.addField("fn", firstName);
        procedure.addField("ln", lastName);
        procedure.addField("sx", sex);
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public boolean updateStudentMealLimit(long id, byte limit) throws SQLException {
        Procedure procedure = new Procedure("UpdateStudentMealLimit");
        procedure.addField("ri", id);
        procedure.addField("ml", limit);
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public boolean delete(long id) throws SQLException {
        Procedure procedure = new Procedure("RemoveStudent");
        procedure.addField("ri", id);
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    private Student studentRSV(ResultSet set) throws SQLException {
        return new Student(
                set.getLong(1),
                set.getLong(2),
                set.getString(3),
                set.getString(4),
                set.getByte(5),
                set.getByte(6)
        );
    }
}