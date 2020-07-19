package com.example.jeewan.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jeewan.R;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Signup");
        //getActionBar().setHomeButtonEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_signup);
        username = (EditText)findViewById(R.id.username_login);
        password = (EditText)findViewById(R.id.password);
        loading = (ProgressBar)findViewById(R.id.loading);
        progressDialog = new ProgressDialog(this);

    }

    public void signuphere(View view) {
        loading.setVisibility(View.VISIBLE);
        String email = username.getText().toString();
        String pass = password.getText().toString();
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            loading.setVisibility(View.INVISIBLE);
                            username.setText("");
                            password.setText("");
                            Toast.makeText(signup.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(signup.this, loginginscreen.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            loading.setVisibility(View.INVISIBLE);
                            username.setText("");
                            password.setText("");
                            Toast.makeText(signup.this, "User Already Registered", Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }

    public void logintextview(View view) {
        Intent intent = new Intent(signup.this, loginginscreen.class);
        startActivity(intent);
        finish();
    }
}