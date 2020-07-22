package com.example.jeewan.menuIeams;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jeewan.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class send_feedback extends AppCompatActivity {
    RatingBar ratingBar;
    EditText feedback;
    Button submit;
    Float ratedValue;

    FirebaseDatabase database;
    DatabaseReference myRef;
    feedbackform feedbackform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        feedback = (EditText)findViewById(R.id.feedback);
        database = FirebaseDatabase.getInstance();
        feedbackform = new feedbackform();
        myRef = database.getReference("feedbackform");
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratedValue = ratingBar.getRating();
            }
        });
        submit = (Button)findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackform.setRating(ratedValue.toString());
                feedbackform.setMessage(feedback.getText().toString().trim());
                myRef.push().setValue(feedbackform);
                //Toast.makeText(send_feedback.this, "Feedback received", Toast.LENGTH_SHORT).show();
            }
        });
    }
}