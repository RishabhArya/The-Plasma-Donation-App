package com.example.jeewan.menuIeams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jeewan.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Send_Feedback extends AppCompatActivity {
    RatingBar ratingBar;
    EditText feedback;
    Button submit;
    Float ratedValue;
    Feedbackform feedbackform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_send_feedback);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setRating(1);
        ratedValue=ratingBar.getRating();
        feedback = (EditText)findViewById(R.id.feedback);
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
                submit.setEnabled(false);
                if(feedback.getText().toString().isEmpty())
                {
                    submit.setEnabled(true);
                    feedback.setError("Enter your feedback");
                    return;
                }

                //add feedback in in firestore
                feedbackform=new Feedbackform(ratedValue.toString(),feedback.getText().toString().trim());
                FirebaseFirestore.getInstance().collection("Feedback").
                        document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(feedbackform)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getApplicationContext(),FeedbackSubmitted.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                submit.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"Something went wrong,Please try again later!",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}