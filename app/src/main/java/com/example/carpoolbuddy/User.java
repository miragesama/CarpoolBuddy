package com.example.carpoolbuddy;

import java.util.ArrayList;

public class User {
    private Integer uid;
    private String email;
    private String name;
    private String type;
    private Double priceMultiplier;
    private ArrayList<String> ownedVehicles;

    public User()
    {

    }

    public User(Integer someUid, String someEmail, String someType)
    {
        this.uid = someUid;
        this.email = someEmail;
        this.type = someType;
    }

    public User(Integer someUid, String someEmail, String someName, String someType, Double someMultiplier)
    {
        this.uid = someUid;
        this.email = someEmail;
        this.name = someName;
        this.type = someType;
        this.priceMultiplier = someMultiplier;
    }

    public Integer getUid(){
        return this.uid;
    }

    public String getEmail(){
        return this.email;
    }

    public String getType(){
        return this.type;
    }

}
