package com.example.PCCircuit;

import java.util.ArrayList;

/**
 * This class is a parent class for Staff and Customer
 * It contains common properties of user such as email, user type and user name
 * It also contains two ArrayList of Projects
 *
 * @author adrianlee
 * @version 1.0
 */
public class User {
    private String uid;
    private String email;
    private String userName = "Someone";
    private String userType;
    private ArrayList<Project> ownedProjects = new ArrayList<>();
    private Integer year_joined;
    private int avg_rating;

    public User()
    {

    }

    public User(String someUid, String someEmail, String someUserType)
    {
        this.uid = someUid;
        this.email = someEmail;
        this.userType = someUserType;
        this.avg_rating = 5;
        this.year_joined = 2021;
    }

    public User(String someUid, String someEmail, String someName, String someUserType)
    {
        this.uid = someUid;
        this.email = someEmail;
        this.userName = someName;
        this.userType = someUserType;
        this.avg_rating = 5;
    }

    /**
     * method to return projects owned by the user
     * @return
     */
    public ArrayList<Project> getProject()
    {
        return this.ownedProjects;
    }

    public void addProject(Project myProject){
        this.ownedProjects.add(myProject);
    }

    public String getUid(){
        return this.uid;
    }

    public String getEmail(){
        return this.email;
    }

    public String getUserType(){
        return this.userType;
    }

    public String getName() { return this.userName;}

    public Integer getYearJoined() { return this.year_joined;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserID(String userID) {
        this.uid = uid;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
