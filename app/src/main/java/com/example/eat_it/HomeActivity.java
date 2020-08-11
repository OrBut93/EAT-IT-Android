package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.eat_it.model.Recommend;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements RecListFragment.Delegate {

    RecyclerView list;
    NavController navCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navCtrl = Navigation.findNavController(this, R.id.home_nav_host);
        NavigationUI.setupActionBarWithNavController(this, navCtrl);

        BottomNavigationView bottonNav = findViewById(R.id.home_bottom_nav);
        NavigationUI.setupWithNavController(bottonNav, navCtrl);

    }

    @Override
    public void onItemSelected(Recommend recommend) {
        NavController navCtrl = Navigation.findNavController(this, R.id.home_nav_host);

//        navCtrl.navigate(R.id.action_recListFragment_to_recommendDetailsFragment);
//        RecListFragmentDirections.ActionRecListFragmentToRecommendDetailsFragment2 directions = RecListFragmentDirections.actionRecListFragmentToRecommendDetailsFragment2(recommend);
//        navCtrl.navigate(directions);

        NavGraphDirections.ActionGlobalRecommendDetailsFragment directions = RecListFragmentDirections.actionGlobalRecommendDetailsFragment(recommend);
        navCtrl.navigate(directions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.recommends_list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                navCtrl.navigateUp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}