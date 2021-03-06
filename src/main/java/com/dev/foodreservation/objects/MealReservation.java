package com.dev.foodreservation.objects;

import java.sql.Timestamp;
import java.util.Date;

public class MealReservation {
    private int id;
    private long
            studentRollId,
            mealCalendarId,
            kitchenId;
    private Date date;
    private Timestamp timestamp;
    private boolean valid;

    public MealReservation(int id){
        this.id = id;
    }

    public MealReservation(int id, long studentRollId, long mealCalendarId, long kitchenId, Date date, Timestamp timestamp, boolean valid) {
        this.id = id;
        this.studentRollId = studentRollId;
        this.mealCalendarId = mealCalendarId;
        this.kitchenId = kitchenId;
        this.date = date;
        this.timestamp = timestamp;
        this.valid = valid;
    }

    public void setMealCalendarId(long mealCalendarId) {
        this.mealCalendarId = mealCalendarId;
    }

    public void setKitchenId(long kitchenId) {
        this.kitchenId = kitchenId;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getId() {
        return id;
    }

    public long getStudentRollId() {
        return studentRollId;
    }

    public long getMealCalendarId() {
        return mealCalendarId;
    }

    public long getKitchenId() {
        return kitchenId;
    }

    public Date getDate() {
        return date;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public boolean isValid() {
        return valid;
    }
}