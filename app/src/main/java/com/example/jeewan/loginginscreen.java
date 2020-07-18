package com.example.jeewan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginginscreen extends AppCompatActivity {

    ProgressBar loading;
    EditText username;
    EditText password;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginginscreen);
        setTitle("LogIn");
        username = (EditText)findViewById(R.id.username_login);
        password = (EditText)findViewById(R.id.password_login);
        loading = (ProgressBar)findViewById(R.id.loading_login);
    }

    public void signinhere(View view) {
        loading.setVisibility(View.VISIBLE);
        String email = username.getText().toString();
        String pass = password.getText().toString();

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            loading.setVisibility(View.INVISIBLE);
                            username.setText("");
                            password.setText("");
                            Toast.makeText(loginginscreen.this, "Login Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(loginginscreen.this, dummyActivity.class);
                            intent.putExtra("email", mAuth.getCurrentUser().getEmail());
                            intent.putExtra("uid", mAuth.getCurrentUser().getUid());
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            // If sign in fails, display a message to the user.
                            loading.setVisibility(View.INVISIBLE);
                            username.setText("");
                            password.setText("");
                            Toast.makeText(loginginscreen.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }

}