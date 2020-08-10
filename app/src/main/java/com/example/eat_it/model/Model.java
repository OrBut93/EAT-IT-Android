package com.example.eat_it.model;

import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;

public class Model {

    public interface Listener<T>{
        void onComplete(T data);
    }

    static public final Model instance = new Model();
    private AsyncTask<String, String, List<Recommend>> MyTask;

    private Model(){
    }

    public  void getAllRecommenda(final Listener<List<Recommend>> listener){

        MyTask = new AsyncTask<String,String,List<Recommend>>(){

            @Override
            protected List<Recommend> doInBackground(String... strings) {
                return AppLocalDb.db.recommendDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Recommend> recommends) {
                super.onPostExecute(recommends);
                listener.onComplete(recommends);
            }
        };

        MyTask.execute();
//        class MyAsynchTask extends AsyncTask<String,String,String>
//        {
//            List<Recommend> data;
//            @Override
//            protected String doInBackground(String... strings) {
////                for(int i=0; i<10;i++) {
////                    AppLocalDb.db.recommendDao().insertAll(new Recommend("id"+i, "title" + i,  "location"+i, "desc"+i, null));
////                }
//                data = AppLocalDb.db.recommendDao().getAll();
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                listener.onComplete(data);
//            }
//        }
//
//        MyAsynchTask task = new MyAsynchTask();

    }



    public Recommend getRecommend(String id){
        return null;
    }


    void updateRec(Recommend recommend) {
    }


    void deleteRec(String id) {
    }


    void addRec(Recommend recommend) {
    }


}
