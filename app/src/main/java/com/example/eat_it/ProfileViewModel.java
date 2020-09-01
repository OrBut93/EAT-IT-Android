package com.example.eat_it;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.eat_it.model.Recommend;
import com.example.eat_it.model.RecommendModel;
import com.example.eat_it.model.User.UserModel;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    public LiveData<List<Recommend>> getData() {
        return RecommendModel.instance.getUserRecommends(UserModel.instance.getCurrentUser());
    }

    public void logout() {
        UserModel.instance.logout();
    }
}
