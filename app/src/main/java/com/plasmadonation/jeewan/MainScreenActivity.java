package com.plasmadonation.jeewan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.plasmadonation.jeewan.covidUpdates.CovidUpdateFragment;
import com.plasmadonation.jeewan.donate.DonateFragment;
import com.plasmadonation.jeewan.menuIeams.AboutUs;
import com.plasmadonation.jeewan.menuIeams.Send_Feedback;
import com.plasmadonation.jeewan.profile.ProfileFragment;
import com.plasmadonation.jeewan.request.RequestFragment;
import com.plasmadonation.jeewan.request.RequestFragment2;
import com.plasmadonation.jeewan.request.RequestModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainScreenActivity extends AppCompatActivity {
    //references to bottom nav view,framelayout and fragments
    private BottomNavigationView bNavView;
    private FrameLayout frameLayout;
    private RequestFragment requestFragment;
    private ProfileFragment profileFragment;
    private DonateFragment donateFragment;
    private CovidUpdateFragment covidUpdateFragment;
    private RequestFragment2 requestFragment2;
    private  boolean flag=false;
    private boolean docexists=false;
    RequestModel requestModel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_navigation_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //display requestfragment
            case R.id.nav_aboutus:
                Intent intent_aboutus = new Intent(MainScreenActivity.this, AboutUs.class);
                startActivity(intent_aboutus);
                return true;
            case R.id.nav_feedback:
                Intent intent_feedback = new Intent(MainScreenActivity.this, Send_Feedback.class);
                startActivity(intent_feedback);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //Firebase auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        //inflate bottomnavview and framelayout
        bNavView = (BottomNavigationView) findViewById(R.id.bottmnavview);
        frameLayout = (FrameLayout) findViewById(R.id.bottom_nav_frame);

        //create instances of fragments
        requestFragment = new RequestFragment();
        donateFragment = new DonateFragment();
        covidUpdateFragment = new CovidUpdateFragment();
        profileFragment = new ProfileFragment();
        requestFragment2=new RequestFragment2();

        //get request data from user
            FirebaseFirestore.getInstance().collection("Requests").document(firebaseUser.getUid())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(value.exists())
                            {
                                docexists=true;
                            }
                            else
                            {
                                docexists=false;
                            }
                        }
                    });

        //default display messagefragment
        setFragment(donateFragment);

        //set onclick listener on items of bottomnavigationview
        bNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //display requestfragment
                    case R.id.nav_request:
                        setRequestFragment();
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

    private void setRequestFragment()
    {
        if(docexists==true)
        {
            setFragment(requestFragment2);
        }
        else
        {
            setFragment(requestFragment);
        }
    }

    //stop backward motion
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}