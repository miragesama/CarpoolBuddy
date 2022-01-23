package com.example.carpoolbuddy;

import java.util.ArrayList;

public class Vehicle {
    private String vehicleType;
    private Integer capacity;
    private String model;
    private String bestPrice;
    private String userEmail;
    private Integer vehicleID;
    private boolean openForBooking;
    private ArrayList<String> riders;

    public Vehicle()
    {

    }

    public Vehicle(String someType, String someModel)
    {
        this.vehicleType = someType;
        this.model = someModel;
    }

    public Vehicle(String someType, Integer someCapacity, String someModel)
    {
        this.vehicleType = someType;
        this.capacity = someCapacity;
        this.model = someModel;
    }

    public Vehicle(String someType, Integer someCapacity, String someModel, String somePrice)
    {
        this.vehicleType = someType;
        this.capacity = someCapacity;
        this.model = someModel;
        this.bestPrice = somePrice;
    }

    public String getVehicleType()
    {
        return this.vehicleType;
    }

    public String getModel()
    {
        return this.model;
    }

    public Integer getCapacity()
    {
        return this.capacity;
    }
    public String getBestPrice()
    {
        return this.bestPrice;
    }

    public void setVehicleType(String myVehicleType){
        this.vehicleType = myVehicleType;
    }
    public void setVehicleCapacity(String myVehicleCapacity){
        this.vehicleType = myVehicleCapacity;
    }
    public void setVehicleModel(String myVehicleModel){
        this.vehicleType = myVehicleModel;
    }
}
