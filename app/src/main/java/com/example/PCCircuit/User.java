package com.example.PCCircuit;

import java.util.ArrayList;

/**
 * This class is a parent class for student, teacher, parent and alumni
 * It contains common properties of user such as email, user type and rider rating
 * It also contains two ArrayList of vehicles, one for vehicle owned by the user,
 * the other for vehicle rode by the user
 *
 * @author adrianlee
 * @version 1.0
 */
public class User {
    private Integer uid;
    private String email;
    private String name = "Someone";
    private String userType;
    private String password;
    private ArrayList<Project> ownedProjects = new ArrayList<>();

    public User()
    {

    }

    public User(Integer someUid, String someEmail, String someUserType)
    {
        this.uid = someUid;
        this.email = someEmail;
        this.userType = someUserType;
    }

    public User(Integer someUid, String someEmail, String someName, String someUserType)
    {
        this.uid = someUid;
        this.email = someEmail;
        this.name = someName;
        this.userType = someUserType;
    }

    /**
     * method to add vehicle owned by the user
     * @param p
     */
    public void addProject(Project p)
    {
        System.out.println("***** at addVehicle method in User obj");
        ownedProjects.add(p);
    }

    /**
     * method to return vehicles owned by the user
     * @return
     */
    public ArrayList<Project> getProject()
    {
        return this.ownedProjects;
    }

    /**
     * method to return vehicles rode by the user
     * @return
     */

    public Integer getUid(){
        return this.uid;
    }

    public String getEmail(){
        return this.email;
    }

    public String getUserType(){
        return this.userType;
    }

    public String getName() { return this.name;}

}
