package com.example.jeewan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.jeewan.authentication.Mainlogin;
import com.example.jeewan.profile.ProfileForm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //hide toolbar
        getSupportActionBar().hide();

        //create new handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentUser != null) {
                    //move to MainScreen activity
                    Intent intent = new Intent(SplashActivity.this, MainScreenActivity.class);
                    startActivity(intent);
                }
                else {
                    //move to activity_mainlogin activity
                    Intent intent = new Intent(SplashActivity.this, Mainlogin.class);
                    startActivity(intent);
                }
            }
        }, 2500);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // get current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }
}
