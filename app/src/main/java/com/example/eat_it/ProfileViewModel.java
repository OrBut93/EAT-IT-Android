//package com.example.eat_it;
//
//import androidx.lifecycle.ViewModel;
//
//import com.example.eat_it.model.RecommendModel;
//import com.example.eat_it.model.User.UserModel;
//
//public class ProfileViewModel extends ViewModel {
//
//    public LiveData<List<Outfit>> getData() {
//        return RecommendModel.instance.getUserOutfits(UserModel.instance.getCurrentUser());
//    }
//
//    public void logout() {
//        UserModel.instance.logout();
//    }
//}
