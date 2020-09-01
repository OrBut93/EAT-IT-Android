package com.example.eat_it;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about2, container, false);;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_about2, container, false);
        return view;
    }
}