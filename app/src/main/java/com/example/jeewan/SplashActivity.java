package com.example.jeewan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //hide actionbar
        getSupportActionBar().hide();

        //create new handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //move to CovidUpdates Activity
                Intent intent = new Intent(SplashActivity.this, CovidUpdates .class);
                startActivity(intent);
            }
        }, 2500);
    }
}
