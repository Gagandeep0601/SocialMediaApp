package com.bignerdranch.socialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    //firebase authentication
    FirebaseAuth mFirebaseAuth;
    TextView mProfileTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mProfileTv = findViewById(R.id.profileTv);
        //init firebase instance
        mFirebaseAuth = FirebaseAuth.getInstance();
        actionBar();
    }

     public void checkUserStatus()
     {
         //get current user
         FirebaseUser user = mFirebaseAuth.getCurrentUser();
         if(user != null)
         {
             // user is signed in to stay in login page
            // Toast.makeText(ProfileActivity.this, "Welcome to your Profile", Toast.LENGTH_SHORT).show();
             mProfileTv.setText(user.getEmail());  // get and set the login user email

         }else
         {
             //if there is no user signed in , go to MainActivity
             Intent intent = new Intent(ProfileActivity.this , MainActivity.class);
             startActivity(intent);
             finish();
         }
     }

    @Override
    protected void onStart() {
     //check on start of app
        checkUserStatus();
        super.onStart();
    }

    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();  //action bar
        actionBar.setTitle("Profile");  //set title of action bar
    }

    // inflate option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle menu click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id = item.getItemId();
        if(id == R.id.action_logout)
        {
            mFirebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
}