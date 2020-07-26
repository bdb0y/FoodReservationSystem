package com.dev.foodreservation.objects;

public class Meal {
    private long id;
    private byte mealType;
    private String name;

    public Meal(long id, byte mt, String name) {
        this.id = id;
        this.mealType = mt;
        this.name = name;
    }

    public void setMealType(byte mt) {
        this.mealType = mt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public byte getMealType() {
        return mealType;
    }

    public String getName() {
        return name;
    }
}
