package com.example.jeewan.request;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.jeewan.profile.ProfileViewModel;

public class RequestViewModelFactory implements ViewModelProvider.Factory {
    String name,reqtype,bgroup,amount,date,hospitalname,city,phoneno,description;


    public RequestViewModelFactory(String name, String req_type, String blood_group, String amount, String date, String hospital_name,
                        String city, String phone_number, String description) {
        this.name = name;
        this.reqtype = req_type;
        this.bgroup = blood_group;
        this.amount = amount;
        this.date = date;
        this.hospitalname = hospital_name;
        this.city = city;
        this.phoneno = phone_number;
        this.description = description;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RequestViewModel.class)){
            return (T) new RequestViewModel(name,reqtype,bgroup,amount,date,hospitalname,city,phoneno,description);
        }
        else {
            return null;
        }
    }
}
