package com.dev.foodreservation.database.interfaces;

import com.dev.foodreservation.objects.MealCalendar;
import com.dev.foodreservation.objects.SetupMealCalendar;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public interface IntMealCalendar {

    boolean addMeal(int mealId, int kitchenId, int total,
                    Date date, Date today) throws SQLException;

    boolean updateMeal(int id, int mealId, int total,
                       Date date, Date today) throws SQLException;

    List<SetupMealCalendar> getMealCalendar(int kitchenId,
                                       Date from,
                                       Date to)
            throws SQLException;
}
