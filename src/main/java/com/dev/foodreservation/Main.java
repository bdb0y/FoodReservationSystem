package com.dev.foodreservation;

import com.dev.foodreservation.database.StudentDAO;
import com.github.mfathi91.time.PersianDate;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) throws SQLException {
        StudentDAO studentDAO = new StudentDAO();
        Student student = new Student(963111135L, 3380923711L,
                "mohammad amin", "rezaeizadeh", Byte.parseByte("0"), Byte.parseByte("1"));
        if (studentDAO.save(student)) System.out.println("inserted successfully");
        else System.out.println("Exists!");
//        Time time = Time.valueOf(LocalTime.now());
        System.out.println(Time.valueOf(LocalTime.now()));
        System.out.println(PersianDate.now());

    }
}