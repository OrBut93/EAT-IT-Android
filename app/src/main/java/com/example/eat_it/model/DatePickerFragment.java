package com.example.eat_it.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.eat_it.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DatePickerFragment newInstance(String param1, String param2) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DatePickerDialog picker = new DatePickerDialog(getContext(),this, 2020,1,1);

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d("TAG", ""+year +"/"+month +"/"+ dayOfMonth);
    }
}