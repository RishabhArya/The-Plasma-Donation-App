package com.example.jeewan.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.jeewan.MainScreenActivity;
import com.example.jeewan.R;
import com.example.jeewan.databinding.ActivityProfileFormBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ProfileForm extends AppCompatActivity {
    String json;
    final String TAG="MAin Activity";
    //set of all states
    Set<String> states;
    ActivityProfileFormBinding profileFormBinding;
    //list of all cities in a state
    HashMap<String, ArrayList<String>> cities;
    ProfileViewModel profileViewModel;
    String city;
    String state;
    ActionBar actionBar;

    SharedPreferences formFilled;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileFormBinding=ActivityProfileFormBinding.inflate(getLayoutInflater());
        setContentView(profileFormBinding.getRoot());
        actionBar=getSupportActionBar();
        actionBar.hide();

        //initialize both set and map with data
        init();

        //populate the state adapter using states set and arrayadapter
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,new ArrayList(states));
        profileFormBinding.profileformStateSpinner.setAdapter(arrayAdapter);
        //set onclick listener on state spinner
        profileFormBinding.profileformStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              String selectedItem=adapterView.getItemAtPosition(i).toString();
              state=selectedItem;
              //populate the city adapter using cities hashmap and arrayadapter
              ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(ProfileForm.this,R.layout.support_simple_spinner_dropdown_item,cities.get(selectedItem));
              profileFormBinding.profileformCitySpinner.setAdapter(arrayAdapter1);
          }
          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {
          }
      });


        profileFormBinding.profileformCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Log.d(TAG, "onCreate: " +cities);




        profileFormBinding.profileformSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileViewModel=new ViewModelProvider(getViewModelStore(),new ProfileViewModelFactory(
                        profileFormBinding.profileformNameTv.getText().toString(),
                        profileFormBinding.profileformAgeTv.getText().toString(),
                        profileFormBinding.profileformContactTv.getText().toString()
                        ,city,state)).get(ProfileViewModel.class);
                profileViewModel.getDataPushed().observe(ProfileForm.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean){
                            editor.putBoolean("formfilled",true);
                            editor.commit();
                            Intent intent=new Intent(ProfileForm.this, MainScreenActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(ProfileForm.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    //method to create list of states and cities from json file
    @SuppressLint("WrongConstant")
    private void init() {
        states=new HashSet<>();
        cities=new HashMap<>();
        formFilled=getSharedPreferences("OnBoardingScreen",MODE_APPEND);
        editor=formFilled.edit();


        try {
            //create a json object by loading cities.json file
            JSONObject obj=new JSONObject(loadJson());
            JSONArray array=obj.getJSONArray("array");
            //get state name from each entries in file and add it in set
            for(int i =0;i<array.length();i++){
                JSONObject jsonObject=array.getJSONObject(i);
                states.add(jsonObject.getString("state"));

                //add the city name in existing list if an entry with given state name key  exists
                if(cities.containsKey(jsonObject.getString("state"))){
                     ArrayList<String> tempStrings=cities.get(jsonObject.getString("state"));
                     tempStrings.add(jsonObject.getString("name"));
                     cities.put(jsonObject.getString("state"),tempStrings);
                }
                //otherwise create a new list and add entry with state name as key
                else{
                    ArrayList<String> tempStrings=new ArrayList<>();
                    tempStrings.add(jsonObject.getString("name"));
                    cities.put(jsonObject.getString("state"),tempStrings);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    //method to load data ofjson file in json object
    public String loadJson(){
        try {
            InputStream is = getAssets().open("cities.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            json=new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}




