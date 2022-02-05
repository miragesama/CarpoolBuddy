package com.example.carpoolbuddy;

public class Teacher extends User {
    private String inSchoolTitle;

    public Teacher(Integer uid, String name, String email, String userType, double priceMultiplier, String inSchoolTitle)
    {
        super(uid, name, email, userType, priceMultiplier);
        this.inSchoolTitle = inSchoolTitle;
    }

    public String getInSchoolTitle() {
        return inSchoolTitle;
    }

    public void setInSchoolTitle(String inSchoolTitle) {
        this.inSchoolTitle = inSchoolTitle;
    }
}
