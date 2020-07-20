package com.example.jeewan.request;

import android.content.Context;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jeewan.databinding.FragmentDonateBinding;
import com.example.jeewan.profile.ProfileModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestViewModel extends ViewModel {
    String name,reqtype,bgroup,amount,date,hospitalname,city,phoneno,description;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    List<RequestModel> reqList;

    public RequestViewModel(String name, String req_type, String blood_group, String amount, String date, String hospital_name,
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

        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();
        reqList=new ArrayList<RequestModel>();
    }

    MutableLiveData<Boolean> reqdataPushed=new MutableLiveData<>();
    MutableLiveData<List<RequestModel>> reqDataListFetched=new MutableLiveData<>();



    //method to store request in database
    public MutableLiveData<Boolean> getReqDataPushed() {
        firebaseFirestore.collection("Requests").document(mAuth.getUid()).set(new RequestModel(amount,bgroup,city,date,description,
                hospitalname,name,phoneno,reqtype)).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isComplete()){
                            reqdataPushed.setValue(true);
                        }
                        else{
                            reqdataPushed.setValue(false);
                        }
                    }
                });

        return reqdataPushed;
    }

    //method to retrieve request list from firstore
    public MutableLiveData<List<RequestModel>> getReqDataList(final String search) {

        //get list of requests from firestore
        firebaseFirestore.collection("Requests")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            reqList.clear();
                            if(null==search) {
                                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                    RequestModel request = doc.toObject(RequestModel.class);
                                    if (!(doc.getId().equals(mAuth.getUid()))) {
                                        reqList.add(request);
                                    }
                                }
                            }
                            reqDataListFetched.setValue(reqList);
                        }
                });
        return reqDataListFetched;
    }

    //method to retrieve request list with criteria from firstore
    public MutableLiveData<List<RequestModel>> getReqDataListWithCriteria(String searchcriteria,String searchkeyword) {

        if("Search by City".equals(searchcriteria)) {
            firebaseFirestore.collection("Requests").orderBy("city")
                    .startAt(searchkeyword).endAt(searchkeyword + "\uf8ff")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            reqList.clear();
                            for (QueryDocumentSnapshot doc : value) {
                                RequestModel request = doc.toObject(RequestModel.class);
                                if (!(doc.getId().equals(mAuth.getUid()))) {
                                    reqList.add(request);
                                }
                            }
                            reqDataListFetched.setValue(reqList);
                        }
                    });
        }
        else if("Search by Blood group".equals(searchcriteria)) {
            firebaseFirestore.collection("Requests").orderBy("blood_group")
                    .startAt(searchkeyword).endAt(searchkeyword + "\uf8ff")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            reqList.clear();
                            for (QueryDocumentSnapshot doc : value) {
                                RequestModel request = doc.toObject(RequestModel.class);
                                if (!(doc.getId().equals(mAuth.getUid()))) {
                                    reqList.add(request);
                                }
                            }
                            reqDataListFetched.setValue(reqList);
                        }
                    });
        }
        return reqDataListFetched;
    }
}
