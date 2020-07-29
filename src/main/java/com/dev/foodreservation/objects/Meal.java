package com.dev.foodreservation.objects;

public class Meal {
    private int id;
    private byte mealType;
    private String name;
    private double price;

    public Meal(int id, byte mt, String name, double price) {
        this.id = id;
        this.mealType = mt;
        this.name = name;
        this.price = price;
    }

    public void setMealType(byte mt) {
        this.mealType = mt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public byte getMealType() {
        return mealType;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
