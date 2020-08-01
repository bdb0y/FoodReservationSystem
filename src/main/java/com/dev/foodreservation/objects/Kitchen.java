package com.dev.foodreservation.objects;

public class Kitchen {
    private int id;
    private String name;
    private byte kitchenType;

    public Kitchen(int id, String name, byte kitchenType) {
        this.id = id;
        this.name = name;
        this.kitchenType = kitchenType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte getKitchenType() {
        return kitchenType;
    }

    @Override
    public String toString() {
        return "Kitchen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", kitchenType=" + kitchenType +
                '}';
    }
}
