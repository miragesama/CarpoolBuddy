package com.example.PCCircuit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * This class allows a vehicle owner user to rate a rider
 * Once rating is submitted and updated to firebase, it navigates to User Profile screen
 *
 * @author adrianlee
 * @version 1.0
 */
public class RateStaffActivity extends AppCompatActivity {

    /**
     * This is standard onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_user);
    }

    /**
     * After user enter the rating, update Firebase to average the rating and go back to USer Profile
     * @param v
     */
    public void submitRiderRating(View v)
    {
        Intent intent = new Intent(this, StaffProfileActivity.class);
        startActivity(intent);
    }
}