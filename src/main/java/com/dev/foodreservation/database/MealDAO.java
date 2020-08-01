package com.dev.foodreservation.database;

import com.dev.foodreservation.database.interfaces.IntMeal;
import com.dev.foodreservation.database.utilities.Executor;
import com.dev.foodreservation.database.utilities.Procedure;
import com.dev.foodreservation.objects.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MealDAO implements IntMeal {

    private final Connection connection;
    private final Statement statement;

    public MealDAO() throws SQLException {
        connection = new DatabaseConnection().connect();
        statement = connection.createStatement();
    }

    @Override
    public boolean add(String mealName, byte mealType, double price)
            throws SQLException {
        Procedure procedure = new Procedure("SaveMeal");
        procedure.addField("n", mealName);
        procedure.addField("mt", mealType);
        procedure.addField("p", price);
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public boolean remove(int id) throws SQLException {
        Procedure procedure = new Procedure("removeMeal");
        procedure.addField("mid", id);
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public boolean updateInfo(int id, String name, byte mealType, double price)
            throws SQLException {
        Procedure procedure = new Procedure("UpdateMeal");
        procedure.addField("i", id);
        procedure.addField("n", name);
        procedure.addField("mt", mealType);
        procedure.addField("p", price);
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public boolean reserve(long studentId, int mealCalendarId, int kitchenId,
                           Date date, Time time) throws SQLException {
        Procedure procedure = new Procedure("ReserveMeal");
        procedure.addField("sri", studentId);
        procedure.addField("mci", mealCalendarId);
        procedure.addField("ki", kitchenId);
        procedure.addField("dd", date);
        procedure.addField("tt", time);
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public boolean cancel(int mealReservationId) throws SQLException {
        Procedure procedure = new Procedure("cancelReservedMeal");
        procedure.addField("mri", mealReservationId);
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public boolean update(int mealReservationId, int mealCalendarId,
                          int kitchenId)
            throws SQLException {
        Procedure procedure =
                new Procedure("UpdateMealCalendar");
        procedure.addField("i", mealReservationId);
        procedure.addField("mci", mealCalendarId);
        procedure.addField("ki", kitchenId);
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public List<Meal> idGet(int id) throws SQLException {
        Procedure procedure = new Procedure("getMeal");
        procedure.addField("id", id);
        List<Meal> meals = new ArrayList<>();
        ResultSet resultSet = new Executor(statement).Execute(procedure);

        while (resultSet.next()) meals.add(MealRSV(resultSet));
        return null;
    }

    @Override
    public List<Meal> nameGet(String name) throws SQLException {
        Procedure procedure = new Procedure("GetNameMeal");
        procedure.addField("n", name);
        List<Meal> meals = new ArrayList<>();
        ResultSet resultSet = new Executor(statement).Execute(procedure);

        while (resultSet.next()) meals.add(MealRSV(resultSet));
        return meals;
    }

    private Meal MealRSV(ResultSet set) throws SQLException {
        return new Meal(
                set.getInt(1),
                set.getByte(3),
                set.getString(2),
                set.getDouble(4)
        );
    }
}