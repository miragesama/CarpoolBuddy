package com.example.PCCircuit;

/**
 * This class is a child class of User
 * It has additional properties for Teacher type of User, such as title
 *
 * @author adrianlee
 * @version 1.0
 */
public class Staff extends User {
    private String inSchoolTitle;

    public Staff(Integer uid, String name, String email, String userType)
    {
        super(uid, name, email, userType);
    }

    public String getInSchoolTitle() {
        return inSchoolTitle;
    }

    public void setInSchoolTitle(String inSchoolTitle) {
        this.inSchoolTitle = inSchoolTitle;
    }
}
