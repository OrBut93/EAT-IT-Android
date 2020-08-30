package com.example.eat_it;

import androidx.lifecycle.ViewModel;

import com.example.eat_it.model.User.UserModel;

public class LoginViewModel extends ViewModel {

    public void login(String email, String password, UserModel.Listener listener) {
        UserModel.instance.login(email, password, listener);
    }
}
