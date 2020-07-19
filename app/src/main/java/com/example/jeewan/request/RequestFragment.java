package com.example.jeewan.request;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeewan.R;
import com.example.jeewan.databinding.FragmentDonateBinding;
import com.example.jeewan.databinding.FragmentRequestBinding;

public class RequestFragment extends Fragment {
    FragmentRequestBinding requestBinding;

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
    }
}