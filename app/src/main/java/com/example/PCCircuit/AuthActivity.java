package com.example.PCCircuit;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.rpc.context.AttributeContext;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class allows user to login or signup to the PC Circuit system
 * It uses a spinner to show list of user types such as Customer and Staff
 * For existing user login, it authenticates the user with Firebase
 * For new user signup, it validates the email address entered and
 * password. Then it add the user to Firebase as well as User document
 *
 * @author adrianlee
 * @version 1.0
 */
public class AuthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    // define local parameters
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private EditText emailField;
    private EditText passwordField;
    protected String userType = "Staff";
    protected Spinner spinnerUserTypeField;
    private String TAG= "myTag";

    /**
     * The onCreate method connects to Firebase. If user is already logged in then go to User Profile
     * It prepares some layout items such as Spinner with types of user to select
     * It also links input fields to parameters for later use
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Firebase connection and retrieve authenticated user
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // if already sign in, then go directly to user profile
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        // define spinner for User Type drop down
        spinnerUserTypeField = findViewById(R.id.AA_spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.UserType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserTypeField.setAdapter(adapter);

        userType = spinnerUserTypeField.getSelectedItem().toString();

        // link layout fields to variables
        emailField = findViewById(R.id.editTextEmail);
        passwordField = findViewById(R.id.editTextPassword);
    }

    /**
     * This method takes the login email and password by user, validate with Firebase
     * and if login successfully, then call updateUI method to go to User Profile screen
     * @param v
     */
    public void signIn(View v)
    {
        // fetch login info entered by user
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        System.out.println(String.format("email: %s and password: %s", emailString, passwordString));
        if (emailString.trim().equals("") || passwordString.trim().equals("")) {
            Toast.makeText(AuthActivity.this, "Please input a valid email and password",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // connect to firebase to authenticate the user login info
        mAuth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    /**
     * This method first validates the email is cis.edu email and toast error message if it's not
     * If email is valid, then it adds the user to Firebase authentication database, and call the
     * uploadData() method to add the user to firebase User document
     * It then call the updateUI() method to go to User Profile screen
     *
     * @param v
     */
    public void signUp(View v)
    {
        // receive information entered by user
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();

        //regular expression for validating email address
        String regex = "^[A-Za-z0-9+_.-]+@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";

        // use Pattern and Matcher class to validate email address
        Pattern myPattern = Pattern.compile(regex);
        Matcher myMatcher = myPattern.matcher(emailString);

        // check if it's valid email address, if not, toast error
        if(!myMatcher.matches())
            Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        else {
            mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("SIGN UP", "Successfully signed up the user");
                        FirebaseUser user = mAuth.getCurrentUser();
                        String userIDString = user.getUid();
                        Log.d("AuthActivity", "UserType"+ userType);

                        if(userType.equals("Customer")){
                            User newUser = new User(userIDString, emailString, "Customer");
                            firestore.collection("User").document(user.getUid()).set(newUser);
                        }
                        if(userType.equals("Staff")){
                            User newUser = new User(userIDString, emailString, "Staff");
                            firestore.collection("User").document(user.getUid()).set(newUser);
                        }
                        updateUI(user);
                    }
                    else {
                        Log.w("SIGN UP", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Signup failure: The password is invalid", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        }
    }

    /**
     * This method is used by the signUp method to add the user object to firebase
     * @param currentUser
     */
    public void uploadData(FirebaseUser currentUser)
    {
        String emailString = currentUser.getEmail();
        String userTypeString = spinnerUserTypeField.getSelectedItem().toString();

        // create the User object
        User myUser = new User(currentUser.getUid(), emailString, userTypeString);
        System.out.println("object uid: "+myUser.getUid());
        System.out.println("object email: "+myUser.getEmail());
        System.out.println("object type: "+myUser.getUserType());

        // Add a new document with a generated ID
        firestore.collection("User")
                .add(myUser)
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

    /**
     * This method is called by signUp and signIn method to navigate to User Profile screen
     * once user signed in
     *
     * @param currentUser
     */
    public void updateUI(FirebaseUser currentUser)
    {
        if(currentUser != null) {
            //spinnerUserTypeField.setOnItemSelectedListener(this);
            //String myUserType = spinnerUserTypeField.getSelectedItem().toString();

            if (userType.equals("Staff")) {
                Intent intent = new Intent(this, StaffProfileActivity.class);
                startActivity(intent);
                Log.w("Auth Activity", "Intent to StaffProfileActivity");
            } else {
                Intent intent = new Intent(this, CustomerProfileActivity.class);
                startActivity(intent);
                Log.w("Auth Activity", "Intent to CustomProfileActivity");
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String myText = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), myText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}