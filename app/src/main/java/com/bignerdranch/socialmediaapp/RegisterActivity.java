package com.bignerdranch.socialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText mEmail , mPassword;
    Button mRegisterUser;
    TextView mAlreadyAccount;
    ProgressDialog mProgress;
    private FirebaseAuth mAuth;   //Declare an instance of firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mRegisterUser = findViewById(R.id.register_button);
        mAlreadyAccount = findViewById(R.id.alreadyAccount);
        mProgress = new ProgressDialog(RegisterActivity.this);
        mProgress.setMessage("Registering User....");
        mAuth = FirebaseAuth.getInstance();              //initialize firebase instance
        actionBar();
        clickRegisterButton();
        //handle login TextView click listener
        mAlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));  // jump to LoginActivity
                finish();
            }
        });
    }
    //Register the user
    private void clickRegisterButton() {
        mRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(RegisterActivity.this, "hhh", Toast.LENGTH_SHORT).show();
               String email = mEmail.getText().toString().trim();
               String password = mPassword.getText().toString().trim();
               //validate email and password
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                  // set error for email address
                    mEmail.setError("Invalid Email Address");
                    mEmail.setFocusable(true);
                }
                else if (password.length()<8)
                {
                    // set error for password
                    mPassword.setError("Password length should be at least 8 characters");
                    mPassword.setFocusable(true);
                } else
                {
                    register(email,password); // register user
                }
            }
        });
    }

    private void register(String email, String password) {
        //Toast.makeText(RegisterActivity.this, "button clicked", Toast.LENGTH_SHORT).show();
        mProgress.show();  // progress bar will display

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success , dismiss progress dialog and start Profile activity
                            mProgress.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Registered ..\n"+user.getEmail(),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message.
                            mProgress.dismiss();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if error, dismiss progress dialog and get and show error message
                mProgress.dismiss(); // progress bar will dismiss
                Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    // set action bar in this function
    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();  //action bar
        actionBar.setTitle("Create Account");  //set title of action bar
        // enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();  // go to previous activity
        return super.onSupportNavigateUp();
    }
} 