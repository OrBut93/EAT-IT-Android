package com.example.eat_it;

import android.os.Bundle;

import com.example.eat_it.model.Recommend;
import com.example.eat_it.model.User.UserModel;

public class HomeActivityViewModel {
    public boolean isUserLoggedIn() {
        return UserModel.instance.isUserLoggedIn();
    }

    public Bundle createBundle() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Recommend", new Recommend());
        return bundle;
    }

}
