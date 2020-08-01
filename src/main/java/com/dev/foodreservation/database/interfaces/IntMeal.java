package com.dev.foodreservation.database.interfaces;

import com.dev.foodreservation.objects.Meal;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface IntMeal {

    boolean add(String mealName, byte mealType, double price) throws SQLException;

    boolean remove(int id) throws SQLException;

    boolean updateInfo(int id, String name, byte mealType, double price) throws SQLException;

    boolean reserve(long studentId, int mealCalendarId, int kitchenId,
                 Date date, Time time) throws SQLException;

    boolean cancel(int mealReservationId) throws SQLException;

    boolean update(int mealReservationId, int mealCalendarId, int kitchenId) throws SQLException;

    List<Meal> idGet(int id) throws SQLException;
    List<Meal> nameGet(String name) throws SQLException;
}
