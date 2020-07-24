package com.example.jeewan.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.jeewan.authentication.Mainlogin;
import com.example.jeewan.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;


@SuppressWarnings("ALL")
public class ProfileFragment extends Fragment {
    FragmentProfileBinding profileBinding;
    ProfileViewModel viewModel;
    FirebaseAuth auth;
    ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity(), new ProfileViewModelFactory("", "", "", "", "")).get(ProfileViewModel.class);
        auth = FirebaseAuth.getInstance();
        return profileBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        viewModel.getDataFetched(auth.getUid()).observe(getActivity(), new Observer<ProfileModel>() {
            @Override
            public void onChanged(ProfileModel profileModel) {
                progressDialog.dismiss();
                profileBinding.nameText.setText(profileModel.name);
                profileBinding.ageText.setText(profileModel.age + " yrs");
                profileBinding.ContactText.setText(profileModel.contact);
                profileBinding.cityText.setText(profileModel.city + " ," + profileModel.state);
            }
        });

        profileBinding.signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileBinding.signoutButton.setEnabled(false);
                auth.signOut();
                startActivity(new Intent(getActivity(), Mainlogin.class));
            }
        });
    }
}