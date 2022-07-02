package com.example.PCCircuit;

import java.util.ArrayList;

/**
 * This class is a child class of User
 * It has additional properties for Student type of User, such as year of graduation
 *
 * @author adrianlee
 * @version 1.0
 */
public class Customer extends User{
    private String graduateYear;
    private ArrayList<String> parentUIDs;

    public Customer(Integer uid, String name, String email, String userType, double priceMultiplier,
                    String graduateYear, ArrayList<String> parentUIDs)
    {
        this.graduateYear = graduateYear;
        this.parentUIDs = parentUIDs;
    }

    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }

    public ArrayList<String> getParentUIDs() {
        return parentUIDs;
    }

    public void setParentUIDs(ArrayList<String> parentUIDs) {
        this.parentUIDs = parentUIDs;
    }
}
