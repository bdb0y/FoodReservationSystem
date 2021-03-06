package com.dev.foodreservation.database;

import com.dev.foodreservation.database.interfaces.IntMealCalendar;
import com.dev.foodreservation.database.utilities.DateToPersianDate;
import com.dev.foodreservation.database.utilities.Executor;
import com.dev.foodreservation.database.utilities.Procedure;
import com.dev.foodreservation.objects.MealCalendar;
import com.dev.foodreservation.objects.SetupMealCalendar;
import com.github.mfathi91.time.PersianDate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;

public class MealCalendarDAO implements IntMealCalendar {

    private Connection connection;
    private Statement statement;
    private PersianDate persianDate;

    public MealCalendarDAO() throws SQLException {
        this.connection = new DatabaseConnection().connect();
        this.statement = connection.createStatement();
    }


    @Override
    public boolean addMeal(int mealId, int kitchenId, int total, Date date, Date today) throws SQLException {
        Procedure procedure = new Procedure("SaveMealCalendar");
        procedure.addField("mi", mealId);
        procedure.addField("ki", kitchenId);
        procedure.addField("t", total);
        procedure.addField("d", "'" + date + "'");
        procedure.addField("td", "'" + today + "'");
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public boolean updateMeal(int mealCalendarId, int mealId, int total, Date date,
                              Date today) throws SQLException {
        Procedure procedure = new Procedure("UpdateMealCalendar");
        procedure.addField("i", mealCalendarId);
        procedure.addField("mi", mealId);
        procedure.addField("t", total);
        procedure.addField("d", "'" + date + "'");
        procedure.addField("td", "'" + today + "'");
        return new Executor(statement).ExecuteUpdate(procedure) > 0;
    }

    @Override
    public List<SetupMealCalendar> getMealCalendar(int kitchenId,
                                                   Date from,
                                                   Date to) throws SQLException {
        List<SetupMealCalendar> setupMealCalendarList
                = new ArrayList<>();
        Procedure procedure = new Procedure("GetMealCalendar");
        procedure.addField("fd", "'" + from + "'");
        procedure.addField("td", "'" + to + "'");
        procedure.addField("ki", kitchenId);
        ResultSet resultSet = new Executor(statement).Execute(procedure);

        while (resultSet.next()) {
            SetupMealCalendar setupMealCalendar = MealCalendarRSV(resultSet);
            setupMealCalendar.setKitchenId(kitchenId);
            setupMealCalendarList.add(setupMealCalendar);
        }

        PersianDate fromDate =
                new DateToPersianDate().get(from);
        PersianDate toDate =
                new DateToPersianDate().get(to);
        while (fromDate.compareTo(toDate) <= 0) {
            boolean exists = false;
            for (SetupMealCalendar smc : setupMealCalendarList) {
                if (smc.getDate().toString().equals(fromDate.toString()))
                    exists = true;
            }
            if (!exists) {
                setupMealCalendarList.add(new SetupMealCalendar(
                        Date.valueOf(fromDate.toString()),
                        kitchenId
                ));
            }

            fromDate = fromDate.plusDays(1);
        }
        setupMealCalendarList
                .sort(Comparator.comparing(SetupMealCalendar::getDate));
        return setupMealCalendarList;
    }

    private SetupMealCalendar MealCalendarRSV(ResultSet resultSet) throws SQLException {
        return new SetupMealCalendar(
                resultSet.getDate(1),
                resultSet.getInt(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
                resultSet.getInt(5),
                resultSet.getString(6),
                resultSet.getInt(7),
                resultSet.getInt(8),
                resultSet.getString(9),
                resultSet.getInt(10),
                resultSet.getInt(11),
                resultSet.getString(12),
                resultSet.getInt(13)
        );
    }
}
