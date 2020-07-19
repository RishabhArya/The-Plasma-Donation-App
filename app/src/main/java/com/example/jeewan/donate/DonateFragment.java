package com.example.jeewan.donate;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeewan.R;
import com.example.jeewan.databinding.FragmentCovidUpdateBinding;
import com.example.jeewan.databinding.FragmentDonateBinding;

public class DonateFragment extends Fragment {
    FragmentDonateBinding donateBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        donateBinding = FragmentDonateBinding.inflate(inflater, container, false);

        return donateBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
