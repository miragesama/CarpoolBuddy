package com.example.carpoolbuddy;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Vehicle {
    private String vehicleType;
    private Integer capacity;
    private String model;
    private String bestPrice;
    private String ownerEmail;
    private Integer vehicleID;
    private String openStatus;
    private String rating;
    private ArrayList<String> riders;

    public Vehicle()
    {

    }

    public Vehicle(String someType, String someModel)
    {
        Random rand = new Random();
        int int_randome = rand.nextInt(99999);

        this.vehicleID = int_randome;
        this.vehicleType = someType;
        this.model = someModel;
        this.openStatus = "Open";
        this.rating = "5 Star";
    }

    public Vehicle(String someType, Integer someCapacity, String someModel)
    {
        Random rand = new Random();
        int int_randome = rand.nextInt(99999);

        this.vehicleID = int_randome;
        this.vehicleType = someType;
        this.capacity = someCapacity;
        this.model = someModel;
        this.openStatus = "Open";
        this.rating = "5 Star";
    }

    public Vehicle(String someType, Integer someCapacity, String someModel, String somePrice, String ownerEmail)
    {
        Random rand = new Random();
        int int_randome = rand.nextInt(99999);

        this.vehicleID = int_randome;
        this.vehicleType = someType;
        this.capacity = someCapacity;
        this.model = someModel;
        this.bestPrice = somePrice;
        this.ownerEmail = ownerEmail;
        this.openStatus = "Open";
        this.rating = "5 Star";
    }

    public Vehicle(String someType, Integer someCapacity, String someModel, String somePrice)
    {
        Random rand = new Random();
        int int_randome = rand.nextInt(99999);

        this.vehicleID = int_randome;
        this.vehicleType = someType;
        this.capacity = someCapacity;
        this.model = someModel;
        this.bestPrice = somePrice;
        this.openStatus = "Open";
        this.rating = "5 Star";
    }

    public String getVehicleType()
    {
        return this.vehicleType;
    }

    public String getModel()
    {
        System.out.println("***** at getModel - vID "+this.vehicleID+" model: "+this.model);
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
    public String getOwnerEmail()
    {
        return this.ownerEmail;
    }

    public String getRating()
    {
        return this.rating;
    }

    public Integer getVehicleID()
    {
        return this.vehicleID;
    }

    public String getOpenStatus()
    {
        System.out.println("***** at getOpenStatus - vID "+this.vehicleID+" booking status: "+this.openStatus);
        if(this.openStatus.equals("Open"))
            return "Open";
        else
            return "Closed";
    }

    public void setOpenStatus()
    {
        if(this.openStatus.equals("Open"))
        {
            System.out.println("***** at setOpenStatus - changing to close");
            this.openStatus = "Closed";
        }
        else
        {
            System.out.println("***** at setOpenStatus - changing to open");
            this.openStatus = "Open";
        }

    }

    public void setVehicleCapacityReduceOne()
    {
        this.capacity= this.capacity-1;
    }

    public void setVehicleModel(String myVehicleModel)
    {
        this.vehicleType = myVehicleModel;
    }
}
