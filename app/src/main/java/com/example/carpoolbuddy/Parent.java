package com.example.carpoolbuddy;

import java.util.ArrayList;

public class Parent extends User {
    private ArrayList<String> childrenUIDS;

    public Parent(Integer uid, String name, String email, String userType, double priceMultiplier, ArrayList<String> myChildUID)
    {
        super(uid, name, email, userType, priceMultiplier);
        this.childrenUIDS = myChildUID;
    }

    public ArrayList<String> getChildrenUIDS() {
        return childrenUIDS;
    }

    public void setChildrenUIDS(ArrayList<String> childrenUIDS) {
        this.childrenUIDS = childrenUIDS;
    }
}
