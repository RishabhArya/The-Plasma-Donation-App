package com.theplasmadonation.jeewan.menuIeams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.theplasmadonation.jeewan.MainScreenActivity;
import com.theplasmadonation.jeewan.R;

public class FeedbackSubmitted extends AppCompatActivity {
    Button feeback_backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_submitted);

        feeback_backbutton=(Button)findViewById(R.id.feedback_button);
        feeback_backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainScreenActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainScreenActivity.class));
    }
}