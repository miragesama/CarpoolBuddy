package com.example.PCCircuit;

import java.util.ArrayList;

/**
 * This class is a child class of User
 *
 * @author adrianlee
 * @version 1.0
 */
public class Staff extends User {
    private String email;
    private String userName;
    private String userID;
    private String userType;
    private int rating;


    public Staff(String emailString, String userName, String userIDString, String userTypeString)
    {
        this.email = emailString;
        this.userName = userName;
        this.userID = userIDString;
        this.userType = userTypeString;
        this.rating = 5;
    }

}
