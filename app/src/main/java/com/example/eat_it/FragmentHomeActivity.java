package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.eat_it.model.Recommend;

public class FragmentHomeActivity extends AppCompatActivity implements RecListFragment.Delegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_home);

//        RecListFragment rcFragment = (RecListFragment) getSupportFragmentManager().findFragmentById(R.id.home_recommend_list);
//        rcFragment.setTitle("activity updated this title");
        RecListFragment recListFragment = new RecListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_fragment_container, recListFragment, "TAG");
        transaction.addToBackStack("TAG");
        transaction.commit();
    }

    void openRecommendDetails(Recommend recommend){
        RecommendDetailsFragment recommendDetailsFragment = new RecommendDetailsFragment();
        recommendDetailsFragment.setRecommend(recommend);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_fragment_container, recommendDetailsFragment, "TAG");
        transaction.addToBackStack("TAG");
        transaction.commit();
    }

    @Override
    public void onItemSelected(Recommend recommend) {
        openRecommendDetails(recommend);
    }
}