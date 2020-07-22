package com.example.jeewan.request;

import android.content.Context;
import android.util.Log;
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
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class RequestViewModel extends ViewModel {



    @Override
    protected void onCleared() {
        super.onCleared();
    }

    String name, reqtype, bgroup, amount, date, hospitalname, city, phoneno, description;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    List<RequestModel> reqList;
    final  String TAG="Request View Model";


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

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        reqList = new ArrayList<RequestModel>();
    }

    MutableLiveData<Boolean> reqdataPushed = new MutableLiveData<>();
    MutableLiveData<List<RequestModel>> reqDataListFetched = new MutableLiveData<>();


    //method to store request in database
    public MutableLiveData<Boolean> getReqDataPushed() {
        firebaseFirestore.collection("Requests").document(Objects.requireNonNull(mAuth.getUid())).set(new RequestModel(amount, bgroup, city, date, description,
                hospitalname, name, phoneno, reqtype)).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            reqdataPushed.setValue(true);
                        } else {
                            reqdataPushed.setValue(false);
                        }
                    }
                });

        return reqdataPushed;
    }

    //method to retrieve request list from firstore
    public MutableLiveData<List<RequestModel>> getReqDataList() {
        final List<RequestModel> answer = new ArrayList<>();

        firebaseFirestore.collection("Requests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot documentSnapshot : list) {
                        if(documentSnapshot.getId()!=mAuth.getUid()) {
                            RequestModel requestModel = documentSnapshot.toObject(RequestModel.class);
                            answer.add(requestModel);
                        }
                    }
                    reqDataListFetched.setValue(answer);
                }
            }
        });


        return reqDataListFetched;
    }

    //method to retrieve request list with criteria from firstore
    public MutableLiveData<List<RequestModel>> getReqDataListWithCriteria(String searchcriteria, String searchkeyword) {
        final List<RequestModel> list=new ArrayList<>();

        if ("Search by City".equals(searchcriteria)) {
           firebaseFirestore.collection("Requests").orderBy("city").startAt(searchkeyword).endAt(searchkeyword + "\uf8ff").get()
                   .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                           if(task.isSuccessful()){
                               try {
                                   for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                       list.add(queryDocumentSnapshot.toObject(RequestModel.class));
                                   }
                                   Log.d(TAG, "onComplete: "+ list);
                                   reqDataListFetched.setValue(list);
                               }
                               catch(Exception e){
                                   Log.d(TAG, "onComplete: null " +list);
                                  reqDataListFetched.setValue(list);

                               }
                           }

                       }
                   });
        }
        else if ("Search by Blood group".equals(searchcriteria)) {
            firebaseFirestore.collection("Requests").orderBy("blood_group").startAt(searchkeyword).endAt(searchkeyword + "\uf8ff").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                    if (queryDocumentSnapshot.getId() != mAuth.getUid()) {
                                        list.add(queryDocumentSnapshot.toObject(RequestModel.class));
                                    }
                                }
                                reqDataListFetched.setValue(list);
                            }
                        }
                    });
        }
        return reqDataListFetched;
    }
}
