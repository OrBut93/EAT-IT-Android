package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class FragmentHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_home);

        RecListFragment rcFragment = (RecListFragment) getSupportFragmentManager().findFragmentById(R.id.home_recommend_list);
        rcFragment.setTitle("activity updated this title");

    }
}