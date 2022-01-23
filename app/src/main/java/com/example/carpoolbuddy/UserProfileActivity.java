package com.example.carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    TextView user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // show currently signed in user name
        user_email = (TextView) this.findViewById(R.id.UP_email);
        user_email.setText(mUser.getEmail());
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

}