package com.dev.foodreservation.database;

import com.dev.foodreservation.database.interfaces.IntMeal;
import com.dev.foodreservation.database.utilities.ProcedureExecutor;
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
    public boolean add(String mealName, byte mealType, double price) throws SQLException {
        ProcedureExecutor procedureExecutor = new ProcedureExecutor("SaveMeal");
        procedureExecutor.addVariable("n", mealName);
        procedureExecutor.addVariable("mt", mealType);
        procedureExecutor.addVariable("p", price);
        return ExecuteUpdate(procedureExecutor) > 0;
    }

    @Override
    public boolean remove(int id) throws SQLException {
        ProcedureExecutor procedureExecutor = new ProcedureExecutor("removeMeal");
        procedureExecutor.addVariable("mid", id);
        return ExecuteUpdate(procedureExecutor) > 0;
    }

    @Override
    public boolean updateInfo(int id, String name, byte mealType, double price)
            throws SQLException {
        ProcedureExecutor procedureExecutor = new ProcedureExecutor("UpdateMeal");
        procedureExecutor.addVariable("i", id);
        procedureExecutor.addVariable("n", name);
        procedureExecutor.addVariable("mt", mealType);
        procedureExecutor.addVariable("p", price);
        return ExecuteUpdate(procedureExecutor) > 0;
    }

    @Override
    public boolean reserve(long studentId, int mealCalendarId, int kitchenId, Date date,
                           Time time) throws SQLException {
        ProcedureExecutor procedureExecutor = new ProcedureExecutor("ReserveMeal");
        procedureExecutor.addVariable("sri", studentId);
        procedureExecutor.addVariable("mci", mealCalendarId);
        procedureExecutor.addVariable("ki", kitchenId);
        procedureExecutor.addVariable("dd", date);
        procedureExecutor.addVariable("tt", time);
        return ExecuteUpdate(procedureExecutor) > 0;
    }

    @Override
    public boolean cancel(int mealReservationId) throws SQLException {
        ProcedureExecutor procedureExecutor =
                new ProcedureExecutor("cancelReservedMeal");
        procedureExecutor.addVariable("mri", mealReservationId);
        return ExecuteUpdate(procedureExecutor) > 0;
    }

    @Override
    public boolean update(int mealReservationId, int mealCalendarId, int kitchenId)
            throws SQLException {
        ProcedureExecutor procedureExecutor =
                new ProcedureExecutor("UpdateMealCalendar");
        procedureExecutor.addVariable("i", mealReservationId);
        procedureExecutor.addVariable("mci", mealCalendarId);
        procedureExecutor.addVariable("ki", kitchenId);
        return ExecuteUpdate(procedureExecutor) > 0;
    }

    @Override
    public List<Meal> get(int id) throws SQLException {
        ProcedureExecutor procedureExecutor =
                new ProcedureExecutor("getMeal");
        procedureExecutor.addVariable("id", id);
        List<Meal> meals = new ArrayList<>();
        ResultSet resultSet = Execute(procedureExecutor);

        while(resultSet.next()) meals.add(MealRSV(resultSet));
        return null;
    }

    private int ExecuteUpdate(ProcedureExecutor procedure) throws SQLException {
        return statement.executeUpdate(procedure.get());
    }

    private ResultSet Execute(ProcedureExecutor procedure) throws SQLException {
        return statement.executeQuery(procedure.get());
    }

    private Meal MealRSV(ResultSet set) throws SQLException {
        return new Meal(
                set.getInt("1"),
                set.getByte("3"),
                set.getString("2"),
                set.getDouble("4")
        );
    }
}
