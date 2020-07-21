package com.example.jeewan.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileViewModel extends ViewModel {
    String name, age, contact, city, state;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    public ProfileViewModel(String name, String age, String contact, String city, String state) {
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.city = city;
        this.state = state;
    }

    MutableLiveData<Boolean> dataPushed = new MutableLiveData<>();
    MutableLiveData<ProfileModel> dataFetched = new MutableLiveData<>();

    //method to insert profile data in firestore
    public MutableLiveData<Boolean> getDataPushed() {
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(auth.getUid()).set(new ProfileModel(age, city, contact, name, state)).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            dataPushed.setValue(true);
                        } else {
                            dataPushed.setValue(false);
                        }
                    }
                });

        return dataPushed;
    }

    //method to retrieve profile data from firstore
    public MutableLiveData<ProfileModel> getDataFetched(String uuid) {
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Users").document(uuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ProfileModel profileModel = documentSnapshot.toObject(ProfileModel.class);
                dataFetched.setValue(profileModel);
            }
        });
        return dataFetched;
    }
}
