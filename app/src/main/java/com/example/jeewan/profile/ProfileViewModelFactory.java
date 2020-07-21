package com.example.jeewan.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {
    String name, age, Contact, city, State;

    public ProfileViewModelFactory(String name, String age, String Contact, String city, String State) {
        this.name = name;
        this.age = age;
        this.Contact = Contact;
        this.city = city;
        this.State = State;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {


        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {


            return (T) new ProfileViewModel(name, age, Contact, city, State);
        } else {
            return null;
        }
    }
}
