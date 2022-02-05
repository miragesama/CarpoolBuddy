package com.example.carpoolbuddy;

public class Bicycle extends Vehicle{
    private String bicycleType;
    private int weight;
    private int weightCapacity;

    public Bicycle(String vehicleType, int capacity, String model, String bestPrice, String bicycleType, int weight, int weightCapacity)
    {
        super(vehicleType, capacity, model, bestPrice);
        this.bicycleType = bicycleType;
        this.weight = weight;
        this.weightCapacity = weightCapacity;
    }

    public String getBicycleType() {
        return bicycleType;
    }

    public void setBicycleType(String bicycleType) {
        this.bicycleType = bicycleType;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(int weightCapacity) {
        this.weightCapacity = weightCapacity;
    }
}
