package com.theplasmadonation.jeewan.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.theplasmadonation.jeewan.MainScreenActivity;
import com.theplasmadonation.jeewan.R;
import com.theplasmadonation.jeewan.profile.ProfileForm;
import com.theplasmadonation.jeewan.profile.ProfileModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class loginginscreen extends AppCompatActivity {

    ProgressBar loading;
    EditText username;
    EditText password;
    FirebaseAuth mAuth;
    ProfileModel profileModel;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_loginginscreen);
        setTitle("Login");
        username = (EditText)findViewById(R.id.username_login);
        password = (EditText)findViewById(R.id.password_login);
        loading = (ProgressBar)findViewById(R.id.loading_login);
        login=(Button)findViewById(R.id.login_login);
    }

    public void signinhere(View view) {
        login.setEnabled(false);
        loading.setVisibility(View.VISIBLE);
        String email = username.getText().toString();
        String pass = password.getText().toString();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            username.setError("Enter email");
            login.setEnabled(true);
            return;
        }

        if(TextUtils.isEmpty(pass)){
            password.setError("Enter password");
            login.setEnabled(true);
            return;
        }

        mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                // Sign in success, update UI with the signed-in user's information
                                loading.setVisibility(View.INVISIBLE);
                                Toast.makeText(loginginscreen.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                firebaseFirestore.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        ProfileModel profileModel = documentSnapshot.toObject(ProfileModel.class);
                                        if (profileModel!= null) {
                                            // move to MainActivity activity
                                            Intent intent = new Intent(loginginscreen.this, MainScreenActivity.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            // move Profile activity
                                            Intent intent = new Intent(loginginscreen.this, ProfileForm.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                // If sign in fails, display a message to the user.
                                login.setEnabled(true);
                                loading.setVisibility(View.INVISIBLE);
                                username.setText("");
                                password.setText("");
                                Toast.makeText(loginginscreen.this, "Please verify your email address", Toast.LENGTH_LONG).show();
                            }


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