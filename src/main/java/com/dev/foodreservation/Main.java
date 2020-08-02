package com.dev.foodreservation;

import com.dev.foodreservation.database.StudentDAO;
import com.dev.foodreservation.objects.Student;
import com.dev.foodreservation.objects.WalletTransaction;
import com.github.mfathi91.time.PersianDate;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        StudentDAO studentDAO = new StudentDAO();
        Student student = new Student(96112L, 1233L,
                "amin", "rezaeizadeh", Byte.parseByte("0"), Byte.parseByte("10"));
    if (studentDAO.save(student)) System.out.println("inserted successfully");
        else System.out.println("Exists!");
//        Time time = Time.valueOf(LocalTime.now());
        System.out.println(Time.valueOf(LocalTime.now()));
        System.out.println(PersianDate.now().toString());
        System.out.println(Date.valueOf(PersianDate.now().toString()));

//        List<Student> studentList = studentDAO.getAll();
//        for(Student s : studentList) System.out.println(s.getFirstName());

    }
}