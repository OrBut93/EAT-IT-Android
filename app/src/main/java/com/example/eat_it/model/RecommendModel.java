package com.example.eat_it.model;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;
import androidx.lifecycle.LiveData;
import com.example.eat_it.MyApplication;
import com.example.eat_it.model.User.User;

import java.util.List;

public class RecommendModel {

    static public final RecommendModel instance = new RecommendModel();

    RecommendFirebase modelFirebase;

    public interface Listener<T>{
        void onComplete(T data);
    }
    public interface CompListener{
        void onComplete();
    }
    public RecommendModel(){

    }

    public LiveData<List<Recommend>> getAllRecommends(){
        LiveData<List<Recommend>> liveData = (LiveData<List<Recommend>>) AppLocalDb.db.recommendDao().getAll();
        refreshRecommendList(null);
        return liveData;
    }
    public LiveData<List<Recommend>> getUserRecommends(User currentUser) {
        return AppLocalDb.db.recommendDao().getUserRecommends(currentUser.id);
    }



    public void refreshRecommendList(final CompListener listener){
        long lastUpdated = MyApplication.context.getSharedPreferences("TAG",MODE_PRIVATE).getLong("RecommendsLastUpdateDate",0);

        RecommendFirebase.getAllRecommendsSince(lastUpdated,new Listener<List<Recommend>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Recommend> data) {
                new AsyncTask<String,String,String>(){
                    @Override
                    protected String doInBackground(String... strings) {
                        long lastUpdated = 0;
                        for(Recommend recommend : data){
                            AppLocalDb.db.recommendDao().insertAll(recommend);
                            if (recommend.lastUpdated > lastUpdated) lastUpdated = recommend.lastUpdated;
                        }
                        SharedPreferences.Editor edit = MyApplication.context.getSharedPreferences("TAG", MODE_PRIVATE).edit();
                        edit.putLong("RecommendsLastUpdateDate",lastUpdated);
                        edit.commit();
                        return "";
                    }
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if (listener!=null)  listener.onComplete();
                    }
                }.execute("");
            }
        });
    }



    public void addRec(Recommend recommend,Listener<Boolean> listener) {
        modelFirebase.addRecommend(recommend,listener);
//        AppLocalDb.db.recommendDao().insertAll(recommend);
    }

    public void deleteRecommend(Recommend recommend) {
        RecommendFirebase.deleteRecommend(recommend.id);
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteRecommends(final List<Recommend> recommends) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                for (Recommend recommend : recommends) {
                    AppLocalDb.db.recommendDao().delete(recommend);
                    Log.d("TAG","deleted");
                }
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("TAG", "deleted recommends");
            }
        }.execute("");
    }
}
