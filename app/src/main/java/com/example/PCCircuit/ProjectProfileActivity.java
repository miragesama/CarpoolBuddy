package com.example.PCCircuit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This class allows user to Project information in detail
 * User may also update some of the fields of the project
 * and click to Archive or Delete the project
 *
 * @author adrianlee
 * @version 1.0
 */
public class ProjectProfileActivity extends AppCompatActivity
{

    // define local variables
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String vehicleModel;
    private String projectID;
    private String customerName;
    private String userEmail;
    private String projectType;
    private String spreadUrl;
    private String buildDate;
    private FirebaseFirestore firestore;
    private String TAG= "myTag";
    private Project myProj;

    /**
     * This onCreate method connects to firebase, retrieve current user and link layout items to
     * parameter for displaying vehicle information
     * It receives vehicle information via extras from VehicleInformationActivities
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_profile);

        // retrieve current user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // link layout items to parameter
        TextView ProjectIDText = findViewById(R.id.PP_ProjectID);
        TextView CustomerNameText = findViewById(R.id.PP_CustomerName);
        TextView ProjectTypeText = findViewById(R.id.PP_ProjectType);
        EditText DetailsLinkEdit = (EditText) findViewById(R.id.PP_DetailsURL);
        EditText BuildDateEdit = (EditText) findViewById(R.id.PP_BuildDate);

        // Bundle the data from RecyclerView
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            //vehicleModel = extras.getString("model");
            projectID = extras.getString("projectID");
            customerName = extras.getString("customerName");
            userEmail = extras.getString("customerName");
            projectType = extras.getString("projectType");
            spreadUrl = extras.getString("spreadURL");
            System.out.println("***** getExtra spreadUrl: "+spreadUrl);
            buildDate = extras.getString("buildDate");
            System.out.println("***** end of getExtra");
        }


        FirebaseUser mUser = mAuth.getCurrentUser();

        // Show data on screen
        ProjectIDText.setText(projectID);
        CustomerNameText.setText(customerName);
        ProjectTypeText.setText(projectType);
        DetailsLinkEdit.setText(spreadUrl, TextView.BufferType.EDITABLE);
        BuildDateEdit.setText(buildDate);
    }

    /**
     * when user click on the button, to update firebase to open or close the vehicle
     * @param v
     */
    public void archiveProject(View v)  //openclosevehicle
    {
        // connect to firebase
        System.out.println("***** At archiveProject method");
        firestore = FirebaseFirestore.getInstance();

        // locate the object to update on firebase
        firestore.collection("Project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // retrieve data from firebase and loop to identify current vehicle
                            myProj = new Project();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                myProj = document.toObject(Project.class);

                                // Found the object
                                if(projectID.equals(myProj.getProjectIDString()))
                                {
                                    System.out.println("inside If loop found PID : "+myProj.getProjectID());

                                    // Switch archive status
                                    myProj.setActiveStatus();

                                    // call method to update this object on Firebase
                                    System.out.println("***** pj doc now : "+document.getId());
                                    updateProjectStatus(document.getId(), myProj);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        Toast.makeText(getApplicationContext(), "Project archived!", Toast.LENGTH_SHORT).show();

        // once done, navigate to Vehicle Info screen
            Intent intent = new Intent(this, ProjectInfoActivity.class);
            startActivity(intent);
    }

    /**
     * This method is to update firebase and reduce capacity by one when user book the vehicle
     * also update User object's RiderVehicle arraylist to include the vehicleRode
     * @param v
     */
    public void saveProject(View v)   //bookvehicle
    {
        System.out.println("***** At saveProject method");
        EditText DetailsLinkEdit = (EditText) findViewById(R.id.PP_DetailsURL);
        EditText BuildDateEdit = (EditText) findViewById(R.id.PP_BuildDate);
        String newURL = DetailsLinkEdit.getText().toString();
        String newDate = BuildDateEdit.getText().toString();
        if(newURL.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Spreadsheet link cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(newDate.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Build date cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

                firestore = FirebaseFirestore.getInstance();
                firestore.collection("Project")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // retrieve data from firebase and loop to identify current vehicle
                                    myProj = new Project();
                                    System.out.println("***** inside firestore checkpoint1, project: "+projectID);
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        myProj = document.toObject(Project.class);

                                        // Found the current Project object
                                        if (projectID.equals(myProj.getProjectIDString())) {
                                            System.out.println("*** updating fields of curr proj : " + myProj);

                                            myProj.setSpreadUrl(newURL);
                                            myProj.setbuildDate(newDate);

                                            // call method to update this object on Firebase
                                            updateProjectStatus(document.getId(), myProj);
                                            Toast.makeText(getApplicationContext(), "Project updated!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });

        Toast.makeText(getApplicationContext(), "Project saved!", Toast.LENGTH_SHORT).show();

        // go back to ProjectInfo
        Intent intent = new Intent(this, ProjectInfoActivity.class);
        startActivity(intent);
    }

    /**
     * Update Vehicle object document with updated capacity or openStatus to Firebase
     * @param docID
     * @param v
     */
    public void updateProjectStatus(String docID, Project v)
    {
        // Update Vehicle object with parameter object
        firestore.collection("Project")
                .document(docID)
                .set(v)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
        Log.d(TAG, "DocumentSnapshot updated with ID: " + docID);
    }

    /**
     * Update User object with Vehicle booked to Firebase
     * @param docID
     * @param u
     */
    public void updateUserAddVehicleRode(String docID, User u)
    {
        System.out.println("***** docID is : "+docID);
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

    /**
     * navigate to Rate Vehicle screen and pass the vehicle information
     * @param v
     */
    public void deleteProject(View v)  //ratevehicle
    {
        // connect to firebase
        System.out.println("***** At archiveProject method");
        firestore = FirebaseFirestore.getInstance();

        // locate the object to update on firebase
        firestore.collection("Project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // retrieve data from firebase and loop to identify current vehicle
                            myProj = new Project();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                myProj = document.toObject(Project.class);

                                // Found the object
                                if(projectID.equals(myProj.getProjectIDString()))
                                {
                                    System.out.println("inside If loop found PID : "+myProj.getProjectID());

                                    myProj.setActiveStatus();
                                    updateProjectStatus(document.getId(), myProj);

                                    // delete data
                                    firestore.collection("Project")
                                            .document()
                                            .delete()
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error updating document", e);
                                                }
                                            });
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        Toast.makeText(getApplicationContext(), "Project deleted!", Toast.LENGTH_SHORT).show();

        // once done, navigate to Vehicle Info screen
        Intent intent = new Intent(this, ProjectInfoActivity.class);
        startActivity(intent);
    }

    public void goButton(View v)
    {
        EditText DetailsLinkEdit = (EditText) findViewById(R.id.PP_DetailsURL);
        String myURL = DetailsLinkEdit.getText().toString();

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(myURL));
        startActivity(intent);
    }
}