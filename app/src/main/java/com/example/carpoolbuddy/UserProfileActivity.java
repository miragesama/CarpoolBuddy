package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;
    private User myUserObj;
    private String TAG= "myTag";
    private User currUserObj;

    TextView user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        // show currently signed in user name
        user_email = (TextView) this.findViewById(R.id.UP_email);
        user_email.setText(mUser.getEmail());

        // show additional user information
        TextView user_name = findViewById(R.id.UserProfileName);
        TextView user_type = findViewById(R.id.UserProfileType);
        TextView user_rating = findViewById(R.id.UserProfileRating);

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

                                // Found the User object
                                if(mUser.getEmail().equals(myUserObj.getEmail()))
                                {
                                    user_type.setText(myUserObj.getType());
                                    user_name.setText(myUserObj.getName());
                                    user_rating.setText(myUserObj.getRider_rating().toString());
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

       // user_type.setText(currUserObj.getType());
    }

    // click to signout
   public void signOut(View v)
   {
       FirebaseAuth.getInstance().signOut();
       Intent intent = new Intent(this, AuthActivity.class);
       startActivity(intent);
   }

   // navigate to VehicleInfoActivity
   public void seeVehicles(View v)
   {
       Intent intent = new Intent(this, VehicleInfoActivity.class);
       startActivity(intent);
   }

   public void rateUserButton(View v)
   {
       Intent intent = new Intent(this, RateUserActivity.class);
       startActivity(intent);
   }

   public void addVehicleButton(View v)
   {
       Intent intent = new Intent(this, AddVehicleActivity.class);
       startActivity(intent);
   }

    public void recommendVehicleButton(View v)
    {
        Intent intent = new Intent(this, RecommendedVehicleActivity.class);
        startActivity(intent);
    }
}