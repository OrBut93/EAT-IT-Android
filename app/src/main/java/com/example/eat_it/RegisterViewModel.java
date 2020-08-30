package com.example.eat_it;

import androidx.lifecycle.ViewModel;

import com.example.eat_it.model.User.User;
import com.example.eat_it.model.User.UserModel;

public class RegisterViewModel extends ViewModel {
    public void register(String email, String password, String name, UserModel.Listener listener) {
        User user = new User(name, email);
        UserModel.instance.register(user, password, listener);
    }
}