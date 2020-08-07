package com.dev.foodreservation.objects;

import java.util.Date;

public class MealCalendar {
    private int id,
            mealId;
    private String mealName;
    private byte mealType;
    private int total;
    private Date date;

    public MealCalendar(int id, int mealId, String mealName,
                        byte mealType, int total, Date date) {
        this.id = id;
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealType = mealType;
        this.total = total;
        this.date = date;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public void setMealType(byte mealType) {
        this.mealType = mealType;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getMealId() {
        return mealId;
    }

    public byte getMealType() {
        return mealType;
    }

    public Date getDate() {
        return date;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "MealCalendar{" +
                "id=" + id +
                ", mealId=" + mealId +
                ", mealName='" + mealName + '\'' +
                ", mealType=" + mealType +
                ", total=" + total +
                ", date=" + date +
                '}';
    }
}
