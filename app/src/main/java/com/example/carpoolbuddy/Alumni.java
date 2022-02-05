package com.example.carpoolbuddy;

public class Alumni extends User{
    private int graduateYear;

    public Alumni(Integer uid, String name, String email, String userType, double priceMultiplier, int graduateYear)
    {
        super(uid, name, email, userType, priceMultiplier);
        this.graduateYear = graduateYear;
    }

    public int getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(int graduateYear) {
        this.graduateYear = graduateYear;
    }
}
