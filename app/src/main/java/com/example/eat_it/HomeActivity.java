package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
        NavController navCtrl = Navigation.findNavController(this, R.id.home_nav_host);
//        navCtrl.navigate(R.id.action_recListFragment_to_recommendDetailsFragment);
        RecListFragmentDirections.ActionRecListFragmentToRecommendDetailsFragment2 directions = RecListFragmentDirections.actionRecListFragmentToRecommendDetailsFragment2(recommend);
        navCtrl.navigate(directions);

    }
}