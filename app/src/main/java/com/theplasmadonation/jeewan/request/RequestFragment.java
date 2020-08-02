package com.theplasmadonation.jeewan.request;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.theplasmadonation.jeewan.R;
import com.theplasmadonation.jeewan.databinding.FragmentDonateBinding;
import com.theplasmadonation.jeewan.databinding.FragmentRequestBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestFragment extends Fragment {
    FragmentRequestBinding requestBinding;
    RequestViewModel requestViewModel;
    String reqtype,bgroup,amount,date;
    RequestModel requestModel;
    RequestFragment2 requestFragment2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestBinding=FragmentRequestBinding.inflate(inflater, container, false);

        return requestBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requestFragment2=new RequestFragment2();

        //set value in date edittext
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(new Date());
        requestBinding.requestdateEdt.setText(date);

        //get req type
        requestBinding.requestTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reqtype = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //get selected blood group
        requestBinding.bloodTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bgroup = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //get required amount
        requestBinding.bloodAmountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                amount = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //set on click listener on request submit button
        requestBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (requestBinding.requestnameEdt.getText().toString().isEmpty()) {
                    requestBinding.requestnameEdt.setError("Enter name");
                    return;
                }
                if (requestBinding.requestcityEdt.getText().toString().isEmpty()) {
                    requestBinding.requestcityEdt.setError("Enter City");
                    return;
                }
                if (requestBinding.requesthospitalEdt.getText().toString().isEmpty()) {
                    requestBinding.requesthospitalEdt.setError("Enter Hospital name");
                    return;
                }
                if (requestBinding.requestphoneEdt.getText().toString().isEmpty()) {
                    requestBinding.requestphoneEdt.setError("Enter Phone number");
                    return;
                }
                if (requestBinding.requestdescriptionEdt.getText().toString().isEmpty()) {
                    requestBinding.requestdescriptionEdt.setError("Enter the description");
                    return;
                }


                //create new instance of requestview model

                requestViewModel = new ViewModelProvider(getActivity(), new RequestViewModelFactory(
                        requestBinding.requestnameEdt.getText().toString().trim(), reqtype, bgroup, amount,
                        date, requestBinding.requesthospitalEdt.getText().toString().trim(),
                        requestBinding.requestcityEdt.getText().toString().trim().toUpperCase(),
                        requestBinding.requestphoneEdt.getText().toString().trim(),
                        requestBinding.requestdescriptionEdt.getText().toString().trim())).get(RequestViewModel.class);


                //call getReqDataPushed to store profile data in firebase and set observer on it
                requestViewModel.getReqDataPushed().observe(getActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(getContext(), "Request Created", Toast.LENGTH_LONG).show();
                            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.bottom_nav_frame,requestFragment2);
                            fragmentTransaction.commit();
                        } else {
                            Toast.makeText(getContext(), "Please Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}