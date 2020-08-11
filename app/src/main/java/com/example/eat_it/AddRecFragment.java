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
import android.widget.ImageView;

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
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_add_rec, container, false);
        final Button takePhotoBtn = view.findViewById(R.id.new_rec_takePhoto_btn);
        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        imageView= view.findViewById(R.id.new_rec_image);

        return view;

    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESULT_OK = 0;

    void takePhoto(){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
           Bitmap imageBitmap = (Bitmap) extras.get("data");
           imageView.setImageBitmap(imageBitmap);
        }
    }

}