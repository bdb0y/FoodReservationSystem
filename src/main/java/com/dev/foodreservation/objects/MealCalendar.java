package com.dev.foodreservation.objects;

import java.util.Date;

public class MealCalendar {
    private long id,
                    mealId;
    private byte mealType;
    private Date date;

    public MealCalendar(long id, long mealId, byte mealType, Date date) {
        this.id = id;
        this.mealId = mealId;
        this.mealType = mealType;
        this.date = date;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public void setMealType(byte mealType) {
        this.mealType = mealType;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public long getMealId() {
        return mealId;
    }

    public byte getMealType() {
        return mealType;
    }

    public Date getDate() {
        return date;
    }
}
