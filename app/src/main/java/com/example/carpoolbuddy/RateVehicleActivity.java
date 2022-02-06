package com.example.carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RateVehicleActivity extends AppCompatActivity {

    private String model;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_vehicle);

        TextView modelText = findViewById(R.id.RateVehicleModel);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            model = extras.getString("model");
            System.out.println("***** rate vehicle status#2: "+model);
        }

        //modelText.setText(model);
    }

    // navigate to VehicleInfoActivity
    public void submitRateVehicle(View v)
    {
        Intent intent = new Intent(this, VehicleInfoActivity.class);
        startActivity(intent);
    }
}