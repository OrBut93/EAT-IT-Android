package com.example.eat_it.model;

import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;

public class Model {

//    List<Recommend> recommends = new LinkedList<>();

    static public final Model instance = new Model();

    private Model(){
//        for(int i=0; i<10;i++) {
//            Recommend rc= new Recommend("id"+i, "title" + i,  "location"+i, "desc"+i, null);
//            recommends.add(rc);
//        }
    }

    public interface GetAllRecommendsListener{
        void onComplete(List<Recommend> data);
    }


    public  void getAllRecommenda(final GetAllRecommendsListener listener){

        class MyAsynchTask extends AsyncTask<String,String,String>
        {
            List<Recommend> data;
            @Override
            protected String doInBackground(String... strings) {
//                for(int i=0; i<10;i++) {
//                    AppLocalDb.db.recommendDao().insertAll(new Recommend("id"+i, "title" + i,  "location"+i, "desc"+i, null));
//                }
                data = AppLocalDb.db.recommendDao().getAll();
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                listener.onComplete(data);
            }
        }

        MyAsynchTask task = new MyAsynchTask();
        task.execute();
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
