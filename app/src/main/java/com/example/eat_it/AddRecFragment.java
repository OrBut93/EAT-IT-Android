package com.example.eat_it;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecFragment extends Fragment {

    public AddRecFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_add_rec, container, false);
        final Button takePhotoBtn = view.findViewById(R.id.new_rec_takePhoto_btn);
        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                takePhoto();
            }
        });

        return view;

    }

}