package com.example.jeewan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.jeewan.authentication.mainlogin;
import com.example.jeewan.onboarding.screens.OnBoarding;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences onBoardingScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        getSupportActionBar().hide();

        //create new handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingScreen = getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firsttime",true);
                if(isFirstTime){
                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firsttime",false);
                    editor.commit();
                    Intent intent = new Intent(SplashActivity.this, OnBoarding.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(SplashActivity.this, mainlogin.class);
                    startActivity(intent);
                    finish();

                }

            }
        }, 2500);

    }
}
