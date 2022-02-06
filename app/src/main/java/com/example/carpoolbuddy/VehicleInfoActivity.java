package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class VehicleInfoActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String TAG= "myTag";
    private RecyclerView myRecyclerView;
    private ArrayList<Vehicle> vehList;
    private VehicleAdaptor.RecyclerViewClickListener listener;  // for RV click
    private String vehicleDocID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);

        // retrieve current user and link Firebase
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        myRecyclerView = findViewById(R.id.recyclerView);

        getAndPopulateData();
    }

    public void getAndPopulateData(){

        // retrieve all Vehicles from Firebase
        firestore.collection("Vehicles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            // retrieve data from firebase and put in arraylist
                            vehList = new ArrayList<Vehicle>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                vehList.add(document.toObject(Vehicle.class));
                            }

                            // write content of vehList arraylist for debug
                            for(Vehicle v : vehList){
                                System.out.println("arrayobject: "+v.getModel()+" "+v.getCapacity()+" status "+v.getOpenStatus());
                            }

                            // set RV to display contents from arraylist
                            setOnClickListener();  // for RV click, initialize the listener
                            VehicleAdaptor myAdaptor = new VehicleAdaptor(vehList, listener); // include onClick listener
                            myRecyclerView.setAdapter(myAdaptor);
                            myRecyclerView.setLayoutManager(new LinearLayoutManager(VehicleInfoActivity.this));

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    // onClickListener for RV click to show vehicle profile
    private void setOnClickListener()
    {
        System.out.println("*** at setOnclickListener #1");
        listener = new VehicleAdaptor.RecyclerViewClickListener()
        {
        @Override
        public void onClick (View v,int position)
        {
            System.out.println("*** at setOnclickListner1.5");
      //      Toast.makeText(getApplicationContext(), "TEST", Toast.LENGTH_SHORT).show();  // for debug, can be deleted
            Intent intent = new Intent(getApplicationContext(), VehicleProfileActivity.class);
            intent.putExtra("model", vehList.get(position).getModel());
            intent.putExtra("capacity", vehList.get(position).getCapacity().toString());
            intent.putExtra("owner", vehList.get(position).getOwnerEmail());
            intent.putExtra("type", vehList.get(position).getVehicleType());
            intent.putExtra("rating", vehList.get(position).getRating());
            intent.putExtra("openStatus", vehList.get(position).getOpenStatus());
            intent.putExtra("price", vehList.get(position).getBestPrice());
            intent.putExtra("vID", vehList.get(position).getVehicleID());
            System.out.println("***** Open status#1: "+vehList.get(position).getOpenStatus());
            startActivity(intent);
            finish();
        }
    };
    }

    public void addVehicles(View v)
    {
        Intent intent = new Intent(this, AddVehicleActivity.class);
        startActivity(intent);
    }

    public void clickToRefresh (View v){
        startActivity(new Intent(this, VehicleInfoActivity.class));
        finish();
    }
}