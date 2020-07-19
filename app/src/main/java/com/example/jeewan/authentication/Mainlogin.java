package com.example.jeewan.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jeewan.R;
import com.example.jeewan.profile.ProfileForm;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hbb20.CountryCodePicker;

public class Mainlogin extends AppCompatActivity {

    private static final int RC_SIGN_IN = 101;
    Button Signin;
    //googlesignin client
    GoogleSignInClient mGoogleSignInClient;
    EditText phoneNumber;
    Button generateOtp;
    Button email;
    CountryCodePicker ccp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlogin);
        getSupportActionBar().hide();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        Signin = (Button) findViewById(R.id.googleSignIn);

        //method call to create gso
        processrequest();
        //set onclick listener on google signin button
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call signin method
                signIn();
            }
        });

        //For Phone Number
        phoneNumber = (EditText)findViewById(R.id.editTextPhone);
        ccp = (CountryCodePicker)findViewById(R.id.ccp);
        //register entered number with ccp
        ccp.registerCarrierNumberEditText(phoneNumber);
        generateOtp = (Button)findViewById(R.id.otobutton);
        //set onclick listener on phonelogin button
        generateOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mainlogin.this, ManageOtp.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
            }
        });

        //for email
        email = (Button) findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mainlogin.this, signup.class);
                startActivity(intent);
            }
        });

    }
    //method to create gso
    private void processrequest(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    //main method for google signin
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    //callback method for startActivityForResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
                // ...
            }
        }
    }
    //method for firebase google auth
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, move to ProfileForm activity
                            Intent intent = new Intent(Mainlogin.this, ProfileForm.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Mainlogin.this, "Unable to Signin", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //prevent from going back
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}