package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddVehicleActivity extends AppCompatActivity {

    private EditText maxCapacityField;
    private EditText vehicleModelField;
    private EditText bestPriceField;
    private Spinner spinnerVehicleTypeField;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String TAG= "myTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        // retrieve current user
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // define spinner for Vehicle Type drop down
        spinnerVehicleTypeField = findViewById(R.id.AVA_Spinner);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.VehicleType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicleTypeField.setAdapter(adapter);

        // link other fields to variables
        maxCapacityField = findViewById(R.id.AVA_capacity);
        vehicleModelField = findViewById(R.id.AVA_model);
        bestPriceField = findViewById(R.id.AVA_best_price);

    }

    public void addVehicle(View v)
    {

        System.out.println("at addVehicle method");
        String maxCapacityString = maxCapacityField.getText().toString();
        Integer maxCapacityInt = Integer.parseInt(maxCapacityString);
        String vehicleModelString = vehicleModelField.getText().toString();
        String bestPriceString = bestPriceField.getText().toString();
        String myVehicleTypeString = spinnerVehicleTypeField.getSelectedItem().toString();

        FirebaseUser mUser = mAuth.getCurrentUser();

        Vehicle myVehicle = new Vehicle(myVehicleTypeString, maxCapacityInt, vehicleModelString, bestPriceString);
        System.out.println("myVehicle type is: "+myVehicle.getVehicleType());
        System.out.println("myVehicle model is: "+myVehicle.getModel());
        System.out.println("myVehicle capacity is: "+myVehicle.getCapacity());
        System.out.println("myVehicle best price is: "+myVehicle.getBestPrice());

        // Add a new document with a generated ID
        firestore.collection("Vehicles")
                .add(myVehicle)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}