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
    private String email;
    private String userName;
    private String userID;
    private String userType;

    public Customer(String emailString, String userName, String userIDString, String userTypeString)
    {
        this.email = emailString;
        this.userID = userIDString;
        this.userType = userTypeString;
        this.userName = userName;
    }



}
