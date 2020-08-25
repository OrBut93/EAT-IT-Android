package com.example.eat_it;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.eat_it.model.RecommendModel;
import com.example.eat_it.model.Recommend;

import java.util.List;

public class RecommendListViewModel extends ViewModel {

    LiveData<List<Recommend>> liveData;

    public LiveData<List<Recommend>> getData() {
        if (liveData == null) {
            liveData = RecommendModel.instance.getAllRecommends();
        }
        return liveData;
    }

    public void refresh(RecommendModel.CompListener listener) {
        RecommendModel.instance.refreshRecommendList(listener);
    }
}
