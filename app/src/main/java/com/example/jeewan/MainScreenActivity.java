package com.example.jeewan;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.jeewan.covidUpdates.CovidUpdateFragment;
import com.example.jeewan.donate.DonateFragment;
import com.example.jeewan.profile.ProfileFragment;
import com.example.jeewan.request.RequestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainScreenActivity extends AppCompatActivity {
    //references to bottom nav view,framelayout and fragments
    private BottomNavigationView bNavView;
    private FrameLayout frameLayout;
    private RequestFragment requestFragment;
    private ProfileFragment profileFragment;
    private DonateFragment donateFragment;
    private CovidUpdateFragment covidUpdateFragment;
    private  boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //Firebase auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (!(firebaseUser != null && firebaseUser.isEmailVerified())) {
        }

        //inflate bottomnavview and framelayout
        bNavView = (BottomNavigationView) findViewById(R.id.bottmnavview);
        frameLayout = (FrameLayout) findViewById(R.id.bottom_nav_frame);

        //create instances of fragments
        requestFragment = new RequestFragment();
        donateFragment = new DonateFragment();
        covidUpdateFragment = new CovidUpdateFragment();
        profileFragment = new ProfileFragment();

        //default display messagefragment
        setFragment(donateFragment);

        //set onclick listener on items of bottomnavigationview
        bNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //display requestfragment
                    case R.id.nav_request:
                        setFragment(requestFragment);
                        flag=false;

                        return true;
                    //display donatefragment
                    case R.id.nav_donate:
                        setFragment(donateFragment);
                        flag=true;
                        return true;
                    //display covidupdatefragment
                    case R.id.nav_covidupdates:
                        setFragment(covidUpdateFragment);
                        flag=false;
                        return true;
                    //display profilefragment
                    case R.id.nav_profile:
                        setFragment(profileFragment);
                        flag=false;
                    default:
                        return true;
                }
            }
        });
    }



    //method to replace fragments in display on user click
    private void setFragment(Fragment fragment) {
        //get fragment transaction and replace the framelayout with given fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.bottom_nav_frame, fragment);
        fragmentTransaction.commit();
    }

    //stop backward motion
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}