package com.example.jeewan.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jeewan.MainScreenActivity;
import com.example.jeewan.R;
import com.example.jeewan.profile.ProfileForm;
import com.example.jeewan.profile.ProfileModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class ManageOtp extends AppCompatActivity {
    EditText otp;
    String phoneNumber;
    Button verify;
    String otpid;
    int counter;
    FirebaseAuth mAuth;
    TextView Heading;

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0;
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
                verify.setEnabled(false);
                //display error message if code is empty or not valid
                if (code.isEmpty() || code.length() < 6) {
                    otp.setError("Enter code");
                    otp.requestFocus();
                    verify.setEnabled(true);
                    return;
                }
                //call method for code verification and signin
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                signInWithPhoneAuthCredential(credential);
                }
        });

        //Countdown timer

        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
        mProgressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(60000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                mProgressBar.setProgress((int)i*100/(60000/1000));

            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(100);
                Intent intent = new Intent(ManageOtp.this, Mainlogin.class);
                startActivity(intent);
                finish();
            }
        };
        mCountDownTimer.start();
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
                            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                            firebaseFirestore.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    ProfileModel profileModel = documentSnapshot.toObject(ProfileModel.class);
                                    if (profileModel!= null) {
                                        // move to MainActivity activity
                                        Intent intent = new Intent(ManageOtp.this, MainScreenActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        // move Profile activity
                                        Intent intent = new Intent(ManageOtp.this, ProfileForm.class);
                                        startActivity(intent);
                                    }
                                }
                            });

                        } else {
                            // Sign in failed, display error message
                            Toast.makeText(ManageOtp.this, "Sigh In Code Error", Toast.LENGTH_SHORT).show();
                            verify.setEnabled(true);
                        }
                    }
                });
    }
}
