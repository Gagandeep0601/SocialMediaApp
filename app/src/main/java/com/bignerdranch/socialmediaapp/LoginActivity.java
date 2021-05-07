package com.bignerdranch.socialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    EditText mEmail, mPassword;
    TextView mNotHaveAccount;
    Button mLogin;
    private FirebaseAuth mAuth;
    ProgressDialog mPd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mNotHaveAccount = findViewById(R.id.notAlreadyAccount);
        mLogin = findViewById(R.id.login_button);
        mPd = new ProgressDialog(LoginActivity.this);
        mPd.setMessage("Login user.....");
        //init firebase auth
        mAuth = FirebaseAuth.getInstance();
        actionBar();
        clickLoginButton();
        //not have a account, jump to register Activity
        mNotHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    private void clickLoginButton() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //input data
                String email = mEmail.getText().toString();
                String passw = mPassword.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                     mEmail.setError("Invalid Email Address");
                     mEmail.setFocusable(true);
                }else
                {
                    //login user
                    loginUser(email,passw);
                }

            }
        });
    }

    private void loginUser(String email , String passw) {
        mPd.show();
        mAuth.signInWithEmailAndPassword(email, passw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success , dismiss progress dialog
                            mPd.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            // login , go to ProfileActivity
                            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                        } else {
                            // If sign in fails, display a message to the user. and dismiss progress dialog
                            mPd.dismiss();
                            Toast.makeText(LoginActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //display error message and dismiss progress dialog
                mPd.dismiss();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();;
            }
        });

    }

    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();  //action bar
        actionBar.setTitle("Login");  //set title of action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}