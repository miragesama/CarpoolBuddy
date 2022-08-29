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
import android.widget.Toast;

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
    private EditText projectIdField;
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
        setContentView(R.layout.activity_add_project);
        System.out.println("***************** oncreate check");
        // retrieve current user
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // define spinner for Project Type drop down
        spinnerVehicleTypeField = findViewById(R.id.AVA_Spinner);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.ProjectType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicleTypeField.setAdapter(adapter);

        // link other fields to variables
        maxCapacityField = findViewById(R.id.AVA_capacity);
        vehicleModelField = findViewById(R.id.AVA_model);
        bestPriceField = findViewById(R.id.AVA_best_price);
        projectIdField = findViewById(R.id.AP_myProjectID);

    }

    /**
     * This method takes the input fields and instantiates a new Vehicle object
     * It then add the new vehicle to Firebase
     * It also updated current user's Firebase record to add the vehicle to its arraylist
     *
     * @param v
     */
    public void addProject(View v) {
        System.out.println("****************** begin add project");
        // link layout fields to parameters
        String maxCapacityString = maxCapacityField.getText().toString();
        String vehicleModelString = vehicleModelField.getText().toString();
        String bestPriceString = bestPriceField.getText().toString();
        String myVehicleTypeString = spinnerVehicleTypeField.getSelectedItem().toString();
        String myProjectID = projectIdField.getText().toString();

        //every field must be entered
        if(myProjectID.trim().equals("")){
            Toast.makeText(getApplicationContext(), "Project ID cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(maxCapacityString.trim().equals("")){
            Toast.makeText(getApplicationContext(), "Customer Name cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(vehicleModelString.trim().equals("")){
            Toast.makeText(getApplicationContext(), "Build date cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(bestPriceString.trim().equals("")){
            Toast.makeText(getApplicationContext(), "Spreadsheet link cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        // get current User
        FirebaseUser mUser = mAuth.getCurrentUser();

        // create new Vehicle object
        Project myProject = new Project(myVehicleTypeString, maxCapacityString, vehicleModelString, bestPriceString, mUser.getEmail(), myProjectID);
        System.out.println("*************my proj " + myProject.getProjectType() + maxCapacityString + vehicleModelString + bestPriceString + mUser.getEmail());

        // Add a new project document with a generated ID
        firestore.collection("Project")
                .add(myProject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added Project with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(getApplicationContext(), "Error creating project: "+e, Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getApplicationContext(), "Project added successfully!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(getApplicationContext(), "Error creating Project!", Toast.LENGTH_SHORT).show();
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