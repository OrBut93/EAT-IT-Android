package com.example.eat_it;

import com.example.eat_it.model.Recommend;
import com.example.eat_it.model.RecommendModel;
import com.example.eat_it.model.User.User;
import com.example.eat_it.model.User.UserModel;

public class RecommendDetailsViewModel {
    public User getCurrentUser() {
        return UserModel.instance.getCurrentUser();
    }

    public void deleteRecommend(Recommend recommend) {
        RecommendModel.instance.deleteRecommend(recommend);
    }
}
