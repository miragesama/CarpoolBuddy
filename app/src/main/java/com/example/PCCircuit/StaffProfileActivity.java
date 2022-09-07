package com.example.PCCircuit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This class displays Staff's functions on screen after user logged in
 * There is also a Sign Out button on this screen
 *
 * @author adrianlee
 * @version 1.0
 */
public class StaffProfileActivity extends AppCompatActivity {

    // define local variables
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;
    private User myUserObj;
    private String TAG= "myTag";
    private User currUserObj;
    TextView user_email;

    /**
     * This onCreate method connects to firebase, links textViews with parameters for display
     * And it connects to Firebase to retrieve user's information to show on screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_profile);

        // connections to firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        // show currently signed in user name
        user_email = (TextView) this.findViewById(R.id.UP_email);
        user_email.setText(mUser.getEmail());

        // retrieve user information from firebase
        firestore.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            myUserObj = new User();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                myUserObj = document.toObject(User.class);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /**
     * This method allows user to click to signout
     * @param v
     */
   public void signOut(View v)
   {
       FirebaseAuth.getInstance().signOut();
       Intent intent = new Intent(this, AuthActivity.class);
       startActivity(intent);
   }

    /**
     * This method allows user to navigate to VehicleInfoActivity
     * @param v
     */
   public void seeVehicles(View v)
   {
       Intent intent = new Intent(this, ProjectInfoActivity.class);
       startActivity(intent);
   }

    /**
     * This method allows user to navigate to Rate User screen
     * @param v
     */
   public void rateUserButton(View v)
   {
       Intent intent = new Intent(this, RateStaffActivity.class);
       startActivity(intent);
   }

    /**
     * This method allows user to navigate to Add Vehicle screen
     * @param v
     */
   public void addProjectButton(View v)
   {
       Intent intent = new Intent(this, AddProjectActivity.class);
       startActivity(intent);
   }

    /**
     * This method allows user to navigate to Recommend Vehicle screen
     * @param v
     */
    public void archivedProjectButton(View v)
    {
        Intent intent = new Intent(this, ArchivedInfoActivity.class);
        startActivity(intent);
    }

}