package com.example.jeewan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.jeewan.authentication.Mainlogin;
import com.example.jeewan.onboarding.screens.OnBoarding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    FirebaseUser currentUser;
    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //hide toolbar
        getSupportActionBar().hide();

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               onBoardingScreen = getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);
               boolean isFirstTime = onBoardingScreen.getBoolean("firsttime",true);
               if (currentUser == null) {
                   if(isFirstTime)
                   {
                       //move to OnBoarding activity
                       SharedPreferences.Editor editor = onBoardingScreen.edit();
                       editor.putBoolean("firsttime",false);
                       editor.commit();
                       Intent intent = new Intent(SplashActivity.this, OnBoarding.class);
                       startActivity(intent);
                   }
                   else
                   {
                       //move to activity_mainlogin activity
                       Intent intent = new Intent(SplashActivity.this, Mainlogin.class);
                       startActivity(intent);
                   }
               }
               else
               {
                   //move to MainScreen Activity
                   Intent intent = new Intent(SplashActivity.this, MainScreenActivity.class);
                   startActivity(intent);
               }
           }
       },2500);


    }


    @Override
    protected void onStart() {
        super.onStart();
        // get current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }
}
