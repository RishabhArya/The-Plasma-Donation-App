package com.example.jeewan.donate;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.jeewan.R;
import com.example.jeewan.databinding.FragmentCovidUpdateBinding;
import com.example.jeewan.databinding.FragmentDonateBinding;
import com.example.jeewan.profile.ProfileModel;
import com.example.jeewan.profile.ProfileViewModel;
import com.example.jeewan.profile.ProfileViewModelFactory;
import com.example.jeewan.request.RequestModel;
import com.example.jeewan.request.RequestViewModel;
import com.example.jeewan.request.RequestViewModelFactory;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class DonateFragment extends Fragment {
    FragmentDonateBinding donateBinding;
    RequestViewModel viewModel;
    String search_criteria;
    final String TAG = "Donate Fragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        donateBinding = FragmentDonateBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity(), new RequestViewModelFactory(" ", " ", " ",
                " ", " ", " ", " ", " ", " ")).get(RequestViewModel.class);

        //set linear layout manager for donate_recview
        donateBinding.donateRecyclerview.setHasFixedSize(true);
        donateBinding.donateRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        return donateBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        donateBinding.swipeRefreshLayout.setRefreshing(true);

        //This will fetch all the requests
        viewModel.getReqDataList().observe(requireActivity(), new Observer<List<RequestModel>>() {
            @Override
            public void onChanged(List<RequestModel> requestModels) {
                donateBinding.donateRecyclerview.setAdapter(new DonateAdapter(getContext(), requestModels));
                donateBinding.swipeRefreshLayout.setRefreshing(false);
            }
        });


        //get selected search criteria
        donateBinding.searchCriteriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                search_criteria = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


//
//        donateBinding.searchChoiceEdittext.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//                    d
//                }
//                return false;
//            }
//        });


        donateBinding.searchChoiceEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged: "+ charSequence.length());

                if (charSequence.length() > 0) {
                    donateBinding.swipeRefreshLayout.setRefreshing(true);
                    Log.d(TAG, "onKey: " + donateBinding.searchChoiceEdittext.getText());


                    // We could Use RxJava here using debouncing
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewModel.getReqDataListWithCriteria(search_criteria, donateBinding.searchChoiceEdittext.getText().toString()).observe(getActivity(),
                                    new Observer<List<RequestModel>>() {
                                        @Override
                                        public void onChanged(List<RequestModel> requestModels) {
                                            donateBinding.donateRecyclerview.setAdapter(new DonateAdapter(getActivity(), requestModels));
                                            donateBinding.swipeRefreshLayout.setRefreshing(false);
                                        }
                                    });
                        }
                    },500);

                }




            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        donateBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (donateBinding.searchChoiceEdittext.getText().toString().isEmpty()) {
                    viewModel.getReqDataList().observe(requireActivity(), new Observer<List<RequestModel>>() {
                        @Override
                        public void onChanged(List<RequestModel> requestModels) {
                            donateBinding.donateRecyclerview.setAdapter(new DonateAdapter(getContext(), requestModels));
                            donateBinding.swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
                else{
                    viewModel.getReqDataListWithCriteria(search_criteria, donateBinding.searchChoiceEdittext.getText().toString()).observe(getActivity(),
                            new Observer<List<RequestModel>>() {
                                @Override
                                public void onChanged(List<RequestModel> requestModels) {
                                    donateBinding.donateRecyclerview.setAdapter(new DonateAdapter(getActivity(), requestModels));
                                    donateBinding.swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                }
            }
        });





    }



}
