package com.example.PCCircuit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This class displays all projects on screen using recyclerview
 * It connects to firebase to retrieve all projects and save in an ArrayList
 * It then uses recyclerView with onClickListener to display all projects
 * User may click on a project to see details, the project info is passed to next intent via extras
 * There is a Refresh button that allows users to refresh content of recyclerview after update
 *
 * @author adrianlee
 * @version 1.0
 */
public class ArchivedInfoActivity extends AppCompatActivity {
    // define local variables
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String TAG = "myTag";
    private RecyclerView myRecyclerView;
    private ArrayList<Project> projList;
    private ArchivedProjectAdaptor.RecyclerViewClickListener listener;  // for RV click
    private String vehicleDocID;

    /**
     * This onCreate method connects to firebase, retrieves current user and call method
     * getAndPopulateData() to populate vehicle data
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_project);

        // retrieve current user and link Firebase
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // link the recyclerView layout item to variable
        myRecyclerView = findViewById(R.id.AP_RV);

        // call method to populate vehicle data to RV
        getAndPopulateData();
    }

    /**
     * This method populates all projects from firebase to recyclerview
     */
    public void getAndPopulateData() {
        System.out.println("*** at getAndPopulateData");
        // retrieve all Projects from Firebase
        firestore.collection("Project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // retrieve data from firebase and put in arraylist
                            projList = new ArrayList<Project>();
                            System.out.println("*** at firebase loop");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //Filter to separate archived project into arraylist
                                if (document.get("activeStatus").equals("Archived")) {
                                    projList.add(document.toObject(Project.class));
                                }
                            }

                            // set RV to display contents from arraylist
                            setOnClickListener();  // for RV click, initialize the listener
                            ArchivedProjectAdaptor myAdaptor = new ArchivedProjectAdaptor(projList, listener);
                            // include onClick listener
                            myRecyclerView.setAdapter(myAdaptor);
                            myRecyclerView.setLayoutManager(new LinearLayoutManager
                                    (ArchivedInfoActivity.this));
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /**
     * This method is onClickListener for RV click to show project profile
     */
    private void setOnClickListener() {
        System.out.println("*** at setOnclickListener #1");
        listener = new ArchivedProjectAdaptor.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                // project information to pass to ArchivedProjectProfileActivity intent
                System.out.println("*** at setOnclickListner1.5");
                Intent intent = new Intent(getApplicationContext(), ArchivedProfileActivity.class);
                intent.putExtra("buildDate", projList.get(position).getBuildDate());
                intent.putExtra("projectID", projList.get(position).getProjectID().toString());
                intent.putExtra("customerName", projList.get(position).getcustomerName());
                intent.putExtra("userEmail", projList.get(position).getOwnerEmail());
                intent.putExtra("projectType", projList.get(position).getProjectType());
                intent.putExtra("spreadURL", projList.get(position).getSpreadUrl());
                // System.out.println("***** Open status#1: "+projList.get(position).getOpenStatus());
                startActivity(intent);
                finish();
            }
        };
    }

    /**
     * This method allows user to force refresh data in recyclerview
     * @param v
     */
    public void clickToRefresh (View v){
        startActivity(new Intent(this, ArchivedInfoActivity.class));
        finish();
    }
}
