package com.example.jeewan.request;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jeewan.R;
import com.example.jeewan.databinding.FragmentRequest2Binding;
import com.example.jeewan.databinding.FragmentRequestBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestFragment2 extends Fragment {
    RequestModel requestModel;
    FragmentRequest2Binding request2Binding;
    RequestFragment requestFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        request2Binding = FragmentRequest2Binding.inflate(inflater, container, false);

        return request2Binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requestFragment=new RequestFragment();

        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //get data of previous request of user
        FirebaseFirestore.getInstance().collection("Requests").document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                progressDialog.dismiss();
                requestModel = documentSnapshot.toObject(RequestModel.class);
                request2Binding.requestdisTypeTv.setText(requestModel.getReq_type());
                request2Binding.requestdisNameTvstats.setText(requestModel.getName());
                request2Binding.requestdisBgroupTvstats.setText(requestModel.getBlood_group());
                request2Binding.requestdisBquantTvstats.setText(requestModel.getAmount());
                request2Binding.requestdisHospitalnameTv.setText(((requestModel.getHospital_name().substring(0,1).toUpperCase())+requestModel.getHospital_name().substring(1)) + "," +
                        (requestModel.getCity().substring(0,1)+(requestModel.getCity().substring(1)).toLowerCase()));
                request2Binding.requestdisContactTv.setText(requestModel.getPhone_number());
                request2Binding.requestdisDescTvstats.setText(requestModel.getDescription());
                request2Binding.requestdisDateTv.setText(requestModel.getDate());
            }
        });

        //set listener on button to delete current request
        request2Binding.requestdisCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                FirebaseFirestore.getInstance().collection("Requests").document(FirebaseAuth.getInstance().getUid())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Request deleted",Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(), "Request Created", Toast.LENGTH_LONG).show();
                        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.bottom_nav_frame,requestFragment);
                        fragmentTransaction.commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.show();
                        Toast.makeText(getContext(),"Something went wrong,Please try again later",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}