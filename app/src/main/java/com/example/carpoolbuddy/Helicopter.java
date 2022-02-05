package com.example.carpoolbuddy;

public class Helicopter extends Vehicle{
    private int maxAltitude;
    private int maxAirspeed;

    public Helicopter(String vehicleType, int capacity, String model, String bestPrice, int maxAltitude, int maxAirspeed)
    {
        super(vehicleType, capacity, model, bestPrice);
        this.maxAltitude = maxAltitude;
        this.maxAirspeed = maxAirspeed;
    }

    public int getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(int maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    public int getMaxAirspeed() {
        return maxAirspeed;
    }

    public void setMaxAirspeed(int maxAirspeed) {
        this.maxAirspeed = maxAirspeed;
    }
}
