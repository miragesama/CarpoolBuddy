package com.example.PCCircuit;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is a parent class of car, electric car, bicycle, segway and helicopter
 * It has common properties such as vehicle type, capacity, model, owner email
 * It also has an ArrayList that contains list of user who has ever ride on the vehicle
 * When a vehicle is newly created, its Open-For-Booking status is defauled to Open,
 * it's rating is defaulted to 5 (highest)
 * This class also contains several useful method such as updateOpenCloseStatus, reduceVehicleCapacity
 *
 * @author adrianlee
 * @version 1.0
 */
public class Project {
    private String projectType;
    private String customerName;  // actually customer email
    private String buildDate;
    private String spreadUrl;
    private String ownerEmail;
    private String projectID;
    private String activeStatus;
    private Integer rating;

    public Project()
    {

    }

    public Project(String someType, String someModel)
    {
        this.projectID = "00000";
        this.projectType = someType;
        this.buildDate = someModel;
        this.activeStatus = "Active";
        //this.rating = "5";*/
    }

    public Project(String someType, String someCapacity, String someModel, String myProjectID)
    {
        this.projectID = myProjectID;
        this.projectType = someType;
        this.customerName = someCapacity;
        this.buildDate = someModel;
        this.activeStatus = "Active";
        //this.rating = "5";*/
        }

    //Project(myVehicleTypeString, maxCapacityString, vehicleModelString, bestPriceString, mUser.getEmail(), myProjectID);
    public Project(String someType, String someCapacity, String someModel, String somePrice, String ownerEmail, String myProjectID)
    {
        this.projectID = myProjectID;
        this.projectType = someType;
        this.customerName = someCapacity;
        this.buildDate = someModel;
        this.spreadUrl = somePrice;
        this.ownerEmail = ownerEmail;
        this.activeStatus = "Active";
        //this.rating = "5";*/
    }

    public String getProjectType()
    {
        return this.projectType;
    }

    public String getBuildDate()
    {
        return this.buildDate;
    }

    public String getcustomerName()
    {
        return this.customerName;
    }
    public String getSpreadUrl()
    {
        return this.spreadUrl;
    }
    public String getOwnerEmail()
    {
        return this.ownerEmail;
    }

    /*public String getRating()
    {
        return this.rating;
    }*/

    public String getProjectID()
    {
        return this.projectID;
    }

    public String getProjectIDString() {
       // return Integer.toString(this.projectID);
        return this.projectID;
    }

    /**
     * This method returns the Open status of the vehicle
     * @return
     */
    public String getActiveStatus()
    {
        if(this.activeStatus.equals("Active"))
            return "Active";
        else
            return "Archived";
    }

    /**
     * When this method is called, it flips the Open/Close status of the vehicle
     */
    public void setActiveStatus()
    {
        if(this.activeStatus.equals("Active"))
        {
            this.activeStatus = "Archived";
        }
        else
        {
            this.activeStatus = "Active";
        }

    }

    public void setSpreadUrl(String updatedUrl)
    {
        this.spreadUrl = updatedUrl;
    }

    public void setbuildDate(String updatedBuildDate)
    {
        this.buildDate = updatedBuildDate;
    }

    /**
     * This method reduce the capacity by 1
     * The method is called when a user book this vehicle
     */
    public void setRating(Integer myRating)
    {
        this.rating = myRating;
    }
}
