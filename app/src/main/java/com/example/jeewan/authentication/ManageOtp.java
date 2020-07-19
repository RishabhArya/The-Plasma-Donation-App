package com.example.jeewan.authentication;

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

import com.example.jeewan.R;
import com.example.jeewan.Profile.ProfileForm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ManageOtp extends AppCompatActivity {
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

        //method call to send otp
        initateotp();

        //set on click listener on phone login button to get manually entered code and call method for verification
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get code entered by user
                String code = otp.getText().toString().trim();
                //display error message if code is empty or not valid
                if (code.isEmpty() || code.length() < 6) {
                    otp.setError("Enter code");
                    otp.requestFocus();
                    return;
                }
                //call method for code verification and signin
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                signInWithPhoneAuthCredential(credential);
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
                Intent intent = new Intent(ManageOtp.this, Mainlogin.class);
                startActivity(intent);
                finish();
            }
        };
    }

    //method to send otp
    private void initateotp() {
        //get instance of firebase phoneauthprovider and send and verify message
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)

                //callback after sending cod
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    //get otpid after code is sent to user
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        otpid = s ;
                    }
                    ////to detect sent code automatically and login
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        String code = phoneAuthCredential.getSmsCode();
                        if (code != null) {
                            //set code in edittextotp
                            otp.setText(code);
                            //call method for signin with sent code
                            signInWithPhoneAuthCredential(phoneAuthCredential);
                        }
                    }
                    //show error message if verification fails
                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        Toast.makeText(ManageOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }
    //method to sign in with given credentials
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success,move to Profile Form activity
                            Intent intent = new Intent(ManageOtp.this, ProfileForm.class);
                            startActivity(intent);

                        } else {
                            // Sign in failed, display error message
                            Toast.makeText(ManageOtp.this, "Sigh In Code Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}