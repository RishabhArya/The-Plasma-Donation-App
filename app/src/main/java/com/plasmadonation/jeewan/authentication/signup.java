package com.plasmadonation.jeewan.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.plasmadonation.jeewan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ProgressBar loading;
    EditText username;
    EditText password;
    ProgressDialog progressDialog;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Signup");
        //getActionBar().setHomeButtonEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_signup);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        loading = (ProgressBar)findViewById(R.id.loading);
        progressDialog = new ProgressDialog(this);
        signup=(Button)findViewById(R.id.signup);

    }

    public void signuphere(View view) {
        signup.setEnabled(false);
        loading.setVisibility(View.VISIBLE);
        String email = username.getText().toString();
        String pass = password.getText().toString();
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            username.setError("Enter email");
            signup.setEnabled(true);
            return;
        }

        if(TextUtils.isEmpty(pass)){
            password.setError("Enter password");
            signup.setEnabled(true);
            return;
        }
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    loading.setVisibility(View.INVISIBLE);
                                    if (task.isSuccessful()){
                                    Toast.makeText(signup.this, "Please check your email for verification", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(signup.this, loginginscreen.class);
                                    startActivity(intent);
                                    finish();}
                                }
                            });
                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            // If sign in fails, display a message to the user.
                            signup.setEnabled(true);
                            loading.setVisibility(View.INVISIBLE);
                            username.setText("");
                            password.setText("");
                            Toast.makeText(signup.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void logintextview(View view) {
        Intent intent = new Intent(signup.this, loginginscreen.class);
        startActivity(intent);
        finish();
    }
}