package com.example.parkinglot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
private static int timer=4000;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Intent intentOpenMasterActivity = new Intent(MainActivity.this, MasterActivity.class);
                    startActivity(intentOpenMasterActivity);
                    finish();
                }else {

                    Intent homeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        },timer);
    }
}