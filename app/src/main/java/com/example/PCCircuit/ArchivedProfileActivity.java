package com.example.PCCircuit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ArchivedProfileActivity extends AppCompatActivity {

    // define local variables
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String projectID;
    private String customerName;
    private String userEmail;
    private String projectType;
    private String spreadUrl;
    private String buildDate;
    private FirebaseFirestore firestore;
    private String TAG= "myTag";
    private Project myProj;
    private User myUserObj;

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
        setContentView(R.layout.activity_archived_profile);

        // retrieve current user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // link layout items to parameter
        TextView ProjectIDText = findViewById(R.id.AP_projectID);
        TextView CustomerNameText = findViewById(R.id.AP_custName);
        TextView ProjectTypeText = findViewById(R.id.AP_projType);
        TextView DetailsLinkEdit = findViewById(R.id.AP_URL);
        TextView BuildDateEdit = findViewById(R.id.AP_buildDate);

        // Bundle the data from RecyclerView
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            projectID = extras.getString("projectID");
            customerName = extras.getString("customerName");
            userEmail = extras.getString("customerName");
            projectType = extras.getString("projectType");
            spreadUrl = extras.getString("spreadURL");
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
    public void unArchiveProject(View v)  //openclosevehicle
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
        Intent intent = new Intent(this, ArchivedInfoActivity.class);
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
}