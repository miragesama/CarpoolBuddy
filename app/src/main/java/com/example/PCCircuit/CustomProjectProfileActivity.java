package com.example.PCCircuit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class displays all customer projects on screen using recyclerview
 * It allows user to give rating and leave comments of the project
 *
 * @author adrianlee
 * @version 1.0
 */
public class CustomProjectProfileActivity extends AppCompatActivity {
    private String projectID;
    private String projectType;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;
    private Project myProj;
    private String TAG= "myTag";
    private User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_project_profile);

        // retrieve current user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        FirebaseUser mUser = mAuth.getCurrentUser();

        TextView ProjectIDText = findViewById(R.id.CPP_projectID);
        TextView ProjectTypeText = findViewById(R.id.CPP_projectType);

        // Bundle the data from RecyclerView
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            projectID = extras.getString("projectID");
            projectType = extras.getString("projectType");
            System.out.println("***** end of getExtra");
        }
        User myUser = new User("X10001", "adrianlee0923@gmail.com", "Staff");
        ProjectIDText.setText(projectID);
        ProjectTypeText.setText(projectType);
    }

    public void oneStarClick(View v)
    {
        // call updateDatabase to update rating
        calculateRating(myUser);
        Toast.makeText(getApplicationContext(), "Thanks for the 1 Star rating!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CustomerProfileActivity.class);
        startActivity(intent);
    }

    public void twoStarClick(View v)
    {
        // call updateDatabase to update rating
        calculateRating(myUser);
        Toast.makeText(getApplicationContext(), "Thanks for the 2 Star rating!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CustomerProfileActivity.class);
        startActivity(intent);
    }

    public void threeStarClick(View v)
    {
        // call updateDatabase to update rating
        calculateRating(myUser);
        Toast.makeText(getApplicationContext(), "Thanks for the 3 Star rating!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CustomerProfileActivity.class);
        startActivity(intent);
    }

    public void fourStarClick(View v)
    {
        // call updateDatabase to update rating
        calculateRating(myUser);
        Toast.makeText(getApplicationContext(), "Thanks for the 4 Star rating!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CustomerProfileActivity.class);
        startActivity(intent);
    }

    public void fiveStarClick(View v)
    {
        // call updateDatabase to update rating
        calculateRating(myUser);
        Toast.makeText(getApplicationContext(), "Thanks for the 5 Star rating!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CustomerProfileActivity.class);
        startActivity(intent);
    }

    public void commentClick(View v)
    {
        EditText ProjectCommentText = findViewById(R.id.CPP_comments);
        String projectComment = ProjectCommentText.getText().toString();

        // call updateDatabase to update rating
        Toast.makeText(getApplicationContext(), "Thanks for the comment!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CustomerProfileActivity.class);
        startActivity(intent);
    }

    public void returnCustomerProfile(View v)
    {
        Intent intent = new Intent(this, CustomerProfileActivity.class);
        startActivity(intent);

    }

    public Double calculateRating(User myStaff)
    {
        Double year_join_portion;
        Double num_proj_portion;
        Double cust_rating_portion;
        Double accumRating = 0.00;
        Double overallRating;

        // calculate number of years with company and apply 15%
        int year = Calendar.getInstance().get(Calendar.YEAR);
        year_join_portion = (year-myStaff.getYearJoined()) * 0.15;

        // calculate number of projects completed and apply 15%
        num_proj_portion = myStaff.getProject().size() * 0.15;

        // calculate average customer rating and apply 70%
        for(Project currProj : myStaff.getProject())
        {
            accumRating = accumRating + currProj.getRating();
        }

        cust_rating_portion = accumRating/myStaff.getProject().size() * 0.70;

        // add up for overall rating, format to 2 decimal places
        overallRating = Math.round((cust_rating_portion + num_proj_portion + year_join_portion)* 100.00) / 100.00;

        return overallRating;
        }


    private void updateDatabase(Project p){
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

                                    //myProj.setSpreadUrl(newURL);

                                    // call method to update this object on Firebase
                                    //updateProjectStatus(document.getId(), myProj);
                                    Toast.makeText(getApplicationContext(), "Project updated!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


}