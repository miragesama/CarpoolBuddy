package com.example.PCCircuit;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is a parent class of PC and Keyboard
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
    private Double rating;

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
    public Double getRating()
    {
        return this.rating;
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
     * This method is setter of Rating
     */
    public void setRating(Double myRating)
    {
        this.rating = myRating;
    }
}
