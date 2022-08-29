package com.example.PCCircuit;

/**
 * This class is a child class of Vehicle
 * It has additional properties for car type of vehicle, such as range
 *
 * @author adrianlee
 * @version 1.0
 */
public class PC extends Project {
    private int range;

    public PC(String vehicleType, String capacity, String model, String bestPrice, int range)
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
