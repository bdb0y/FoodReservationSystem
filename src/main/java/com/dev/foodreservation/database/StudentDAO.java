package com.dev.foodreservation.database;

import com.dev.foodreservation.objects.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements DAO<Student> {

    private final Connection connection;
    private final Statement Statement;

    public StudentDAO() throws SQLException {
        connection = new DatabaseConnection().connect();
        Statement = connection.createStatement();
    }

    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        ResultSet set = Statement.executeQuery(
                "EXEC [dbo].[getAllStudents]"
        );
        while (set.next()) students.add(studentRSV(set));
        return students;
    }

    @Override
    public List<Student> rollIdGet(long id) throws SQLException {
        List<Student> students = new ArrayList<>();
        ResultSet set = Statement.executeQuery(
                "EXEC [dbo].[RollIdGetStudent]" +
                        "@ri = " + id
        );
        while (set.next()) students.add(studentRSV(set));
        return students;
    }

    @Override
    public List<Student> IdGet(long id) throws SQLException {
        List<Student> students = new ArrayList<>();
        ResultSet set = Statement.executeQuery(
                "EXEC [dbo].[RollIdGetStudent]" +
                        "@id = " + id
        );
        while (set.next()) students.add(studentRSV(set));
        return students;
    }

    @Override
    public boolean save(Student student) {
        try {
            return Statement.executeUpdate(SAVE_STUDENT(student)) > 0;
        } catch (SQLException exception) {
            return false;
        }
    }

    @Override
    public boolean updateNameSex(long id, String firstName,
                              String lastName, byte sex) throws SQLException {
        return Statement.executeUpdate(
                "EXEC [dbo].[UpdateStudentFirstNameLastName]" +
                        "@ri = "+id+", " +
                        "@fn = "+firstName+", " +
                        "@ln = "+lastName+", " +
                        "@sx = "+sex
        ) > 0;
    }

    @Override
    public boolean updateStudentMealLimit(long id, byte limit) throws SQLException {
        return Statement.executeUpdate(
                "EXEC [dbo].[UpdateStudentMealLimit]" +
                        "@ri ="+id+", " +
                        "@ml ="+limit+", "
        ) > 0;
    }

    @Override
    public boolean delete(long id) throws SQLException {
        return Statement.executeUpdate(
                "EXEC [dbo].[RemoveStudent]" +
                        "@ri ="+id
        ) > 0;
    }

    //Student result set values
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

    private String SAVE_STUDENT(Student student) {
        return "EXEC [dbo].[SaveStudent]" +
                "@ri = " + student.getRollId() + ", " +
                "@i = " + student.getId() + ", " +
                "@fn = " + student.getFirstName() + ", " +
                "@ln = " + student.getLastName() + ", " +
                "@s = " + student.getGender() + ", " +
                "@ml = " + student.getMealLimit() + "";
    }

}