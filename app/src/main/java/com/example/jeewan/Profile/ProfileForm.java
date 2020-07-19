package com.example.jeewan.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.jeewan.R;
import com.example.jeewan.databinding.ActivityProfileFormBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ProfileForm extends AppCompatActivity {
    String json;
    final String TAG="MAin Activity";
    Set<String> states;
    ActivityProfileFormBinding profileFormBinding;
    HashMap<String, ArrayList<String>> cities;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileFormBinding=ActivityProfileFormBinding.inflate(getLayoutInflater());
        setContentView(profileFormBinding.getRoot());
        init();

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,new ArrayList(states));
        profileFormBinding.spinner.setAdapter(arrayAdapter);
        profileFormBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              String selectedItem=adapterView.getItemAtPosition(i).toString();
              ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(ProfileForm.this,R.layout.support_simple_spinner_dropdown_item,cities.get(selectedItem));
              profileFormBinding.spinner2.setAdapter(arrayAdapter1);
              Toast.makeText(ProfileForm.this,selectedItem , Toast.LENGTH_SHORT).show();


          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });

        Log.d(TAG, "onCreate: " +cities);




        profileFormBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }



















    private void init() {
        states=new HashSet<>();
        cities=new HashMap<>();
        try {
            JSONObject obj=new JSONObject(loadJson());
            JSONArray array=obj.getJSONArray("array");
            for(int i =0;i<array.length();i++){
                JSONObject jsonObject=array.getJSONObject(i);
                //Log.d(TAG, "onCreate: "+ jsonObject.getString("name")+" " + jsonObject.getString("state"));
                states.add(jsonObject.getString("state"));

                if(cities.containsKey(jsonObject.getString("state"))){
                     ArrayList<String> tempStrings=cities.get(jsonObject.getString("state"));
                     tempStrings.add(jsonObject.getString("name"));
                     cities.put(jsonObject.getString("state"),tempStrings);
                }
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




