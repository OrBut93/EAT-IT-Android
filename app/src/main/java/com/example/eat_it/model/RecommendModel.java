package com.example.eat_it.model;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import static android.content.Context.MODE_PRIVATE;
import androidx.lifecycle.LiveData;
import com.example.eat_it.MyApplication;
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
        modelFirebase= new RecommendFirebase();
    }

    public void addRec(Recommend recommend,Listener<Boolean> listener) {
        modelFirebase.addRecommend(recommend,listener);
        AppLocalDb.db.recommendDao().insertAll(recommend);
    }

    public void refreshStudentList(final CompListener listener){
        long lastUpdated = MyApplication.context.getSharedPreferences("TAG",MODE_PRIVATE).getLong("RecommendsLastUpdateDate",0);
        modelFirebase.getAllRecommendsSince(lastUpdated,new Listener<List<Recommend>>() {
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
                        edit.putLong("StudentsLastUpdateDate",lastUpdated);
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

    public LiveData<List<Recommend>> getAllRecommends(){
        LiveData<List<Recommend>> liveData = (LiveData<List<Recommend>>) AppLocalDb.db.recommendDao().getAll();
        refreshStudentList(null);
        return liveData;
    }


    public Recommend getRecommend(String id){
        return null;
    }


    void updateRec(Recommend recommend) {
    }

    void deleteRec(String id) {
    }

}
