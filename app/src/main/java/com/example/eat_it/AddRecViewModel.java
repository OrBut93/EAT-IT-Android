package com.example.eat_it;

import com.example.eat_it.model.User.User;
import com.example.eat_it.model.User.UserModel;

public class AddRecViewModel {

    public User getCurrentUser() {
        return UserModel.instance.getCurrentUser();
    }

}
