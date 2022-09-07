package com.example.PCCircuit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * This class allows a customer user to rate a project
 * Once rating is submitted and updated to firebase, it navigates to User Profile screen
 *
 * @author adrianlee
 * @version 1.0
 */
public class RateStaffActivity extends AppCompatActivity {
    // define local variables
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;
    private String TAG= "myTag";
    private RecyclerView myRecyclerView;
    private ArrayList<Project> projList;
    private ProjectAdaptor.RecyclerViewClickListener listener;  // for RV click

    /**
     * This is standard onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_user);

        // retrieve current user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        FirebaseUser mUser = mAuth.getCurrentUser();
    }

    /**
     * After user enter the rating, update Firebase to average the rating and go back to USer Profile
     * @param v
     */
    public void calculateUpdatedRating(View v)
    {


        Intent intent = new Intent(this, StaffProfileActivity.class);
        startActivity(intent);
    }
}