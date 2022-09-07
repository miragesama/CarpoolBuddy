package com.example.PCCircuit;

/**
 * This class is a child class of Project
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
