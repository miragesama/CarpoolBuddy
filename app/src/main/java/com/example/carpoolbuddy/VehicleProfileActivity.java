package com.example.carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class VehicleProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_profile);

        // link layout items to parameter
        TextView nameText = findViewById(R.id.VehicleProfileMdel);
        TextView capacityText = findViewById(R.id.VehicleProfileCapacity);

        // Error handling if no record fetched
        String vehicleModel = "Vehicle Model not set";
        String vehicleCapacity = "Capacity not set";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            vehicleModel = extras.getString("model");
            vehicleCapacity = extras.getString("capacity");
        }

        nameText.setText(vehicleModel);
        capacityText.setText(vehicleCapacity);
    }
}