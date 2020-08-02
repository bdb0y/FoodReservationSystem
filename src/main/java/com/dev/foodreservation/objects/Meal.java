package com.dev.foodreservation.objects;

public class Meal {

    public static final int BREAKFAST = 0;
    public static final int LAUNCH = 1;
    public static final int DINNER = 2;

    private int id;
    private byte type;
    private String name;
    private double price;

    public Meal(int id, byte type, String name, double price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public void setType(byte type) {
        this.type = type;
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

    public byte getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean differs(Meal meal){
        return (!this.name.equals(meal.getName())
                    || this.type != meal.getType()
                        || this.price != meal.price);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
