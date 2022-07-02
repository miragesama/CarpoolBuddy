package com.example.PCCircuit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This class allows user to enter vehicle information for others to book.
 * It uses a spinner item to list types of vehicles that user can add
 * It then add the new Vehicle object onto FireBase Vehicle collection
 * It also update the Firebase User to add this vehicle to user's Arraylist of vehicles
 *
 * @author adrianlee
 * @version 1.0
 */
public class AddProjectActivity extends AppCompatActivity {

    private EditText maxCapacityField;
    private EditText vehicleModelField;
    private EditText bestPriceField;
    private Spinner spinnerVehicleTypeField;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String TAG= "myTag";
    private User myUserObj;
    private String customerEmail;

    /**
     * This onCreate method connects to Firebase, retrieves current user
     * It also set some of the layout items such as spinner values
     * and links the input items to parameters for later user
     *
     * @param savedInstanceState
     */
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

    /**
     * This method takes the input fields and instantiates a new Vehicle object
     * It then add the new vehicle to Firebase
     * It also updated current user's Firebase record to add the vehicle to its arraylist
     *
     * @param v
     */
    public void addVehicle(View v) {

        // link layout fields to parameters
        System.out.println("at addVehicle method");
        String maxCapacityString = maxCapacityField.getText().toString();
        Integer maxCapacityInt = Integer.parseInt(maxCapacityString);
        String vehicleModelString = vehicleModelField.getText().toString();
        String bestPriceString = bestPriceField.getText().toString();
        String myVehicleTypeString = spinnerVehicleTypeField.getSelectedItem().toString();


        // get current User
        FirebaseUser mUser = mAuth.getCurrentUser();

        // create new Vehicle object
        Project myProject = new Project(myVehicleTypeString, maxCapacityInt, vehicleModelString, bestPriceString, mUser.getEmail());

        /* write to log for debugging
        System.out.println("myVehicle type is: " + myVehicle.getVehicleType());
        System.out.println("myVehicle model is: " + myVehicle.getModel());
        System.out.println("myVehicle capacity is: " + myVehicle.getCapacity());
        System.out.println("myVehicle best price is: " + myVehicle.getBestPrice());
        System.out.println("myVehicle email is: " + myVehicle.getOwnerEmail());
        System.out.println("myVehicle ID is: " + myVehicle.getVehicleID());
        System.out.println("myVehicle openStatus is: " + myVehicle.getOpenStatus());*/

        // Add a new Vehicle document with a generated ID
        firestore.collection("Vehicles")
                .add(myProject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added Vehicle with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        // Retrieve the User object from Firestore to add the project to User's ArrayList
        firestore.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // retrieve data from firebase and loop to identify current user
                            myUserObj = new User();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                myUserObj = document.toObject(User.class);
                                // Found the User object
                                if(mUser.getEmail().equals(myUserObj.getEmail()))
                                {
                                    myUserObj.addProject(myProject);
                                    System.out.println("***** docID is : "+document.getId());
                                    updateUserAddProject(document.getId(), myUserObj);
                                    updateCustomerAddProject(customerEmail, myProject);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        // after adding the objects, navigate back to See All Projects
        Intent intent = new Intent(this, ProjectInfoActivity.class);
        startActivity(intent);

    }

    /**
     * This method is called by AddVehicle method to update the User document on Firebase
     * with the parameter User object
     * @param docID
     * @param u
     */
        public void updateUserAddProject(String docID, User u)
        {
        // Update User object to add a vehicle to the user
        firestore.collection("User")
                .document(docID)
                .set(u)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
            Log.d(TAG, "DocumentSnapshot updated with ID: " + docID);
    }

        public void updateCustomerAddProject(String customerEmail, Project p)
        {
            //Update firebase for customer add project to arraylist

        }

}