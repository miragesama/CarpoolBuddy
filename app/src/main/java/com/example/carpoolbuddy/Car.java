package com.example.carpoolbuddy;

public class Car extends Vehicle{
    private int range;

    public Car(String vehicleType, int capacity, String model, String bestPrice, int range)
    {
        super(vehicleType, capacity, model, bestPrice);
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
