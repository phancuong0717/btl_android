package com.example.btl.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.btl.Client.Login;
import com.example.btl.R;
import com.example.btl.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth= FirebaseAuth.getInstance();
        binding=FragmentProfileBinding.inflate(inflater, container,  false);
        binding.logoutButton.setOnClickListener(v->{
            mAuth.signOut();
            Intent intent=new Intent(getActivity(), Login.class);
            startActivity(intent);
            getActivity().finish();
            Toast.makeText(getActivity(), "Sign out successfully", Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }
}