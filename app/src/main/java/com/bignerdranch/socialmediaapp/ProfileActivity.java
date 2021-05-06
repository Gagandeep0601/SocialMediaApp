package com.bignerdranch.socialmediaapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        actionBar();
    }

    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();  //action bar
        actionBar.setTitle("Profile");  //set title of action bar
    }
}