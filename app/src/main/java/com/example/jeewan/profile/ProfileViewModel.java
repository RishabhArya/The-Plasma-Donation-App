package com.example.jeewan.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileViewModel extends ViewModel {
    String name,age,contact,city,state;
    FirebaseAuth auth;

    public ProfileViewModel(String name, String age, String contact, String city, String state) {
        this.name=name;
        this.age=age;
        this.contact=contact;
        this.city=city;
        this.state=state;
    }

    MutableLiveData<Boolean> dataPushed=new MutableLiveData<>();

    public MutableLiveData<Boolean> getDataPushed() {
        auth=FirebaseAuth.getInstance();
        


        return dataPushed;
    }
}
