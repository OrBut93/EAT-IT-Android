package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eat_it.model.Recommend;

public class HomeActivity extends AppCompatActivity implements RecListFragment.Delegate{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onItemSelected(Recommend recommend) {

    }
}