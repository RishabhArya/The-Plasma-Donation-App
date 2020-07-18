package com.example.jeewan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class manageOtp extends AppCompatActivity {
    EditText otp;
    String phoneNumber;
    Button verify;
    String otpid;
    int counter;
    FirebaseAuth mAuth;
    TextView Heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);
        Heading = (TextView)findViewById(R.id.textView10);
        phoneNumber = getIntent().getStringExtra("mobile").toString();
        Heading.setText(Heading.getText().toString() + phoneNumber);
        otp = (EditText)findViewById(R.id.editTextOTP);
        verify = (Button)findViewById(R.id.butVerify);
        mAuth = FirebaseAuth.getInstance();
        initateotp();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().isEmpty()){
                    Toast.makeText(manageOtp.this, "Please Enter Your OTP", Toast.LENGTH_SHORT).show();
                }
                else if(otp.getText().toString().length()!=6){
                    Toast.makeText(manageOtp.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
                else{
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
        //Countdown timer
        final TextView counttime=findViewById(R.id.counttime);
        new CountDownTimer(50000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText(String.valueOf(counter));
                counter++;;
            }
            @Override
            public void onFinish() {

                Intent intent = new Intent(manageOtp.this, mainlogin.class);
                startActivity(intent);
                finish();
            }
        };
    }

    private void initateotp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        otpid = s ;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        Toast.makeText(manageOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(manageOtp.this,CovidUpdates.class);
                            startActivity(intent);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(manageOtp.this, "Sigh In Code Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}