package com.example.eat_it.model;

import java.util.LinkedList;
import java.util.List;

public class Model {

    List<Recommend> recommends = new LinkedList<>();
    static public final Model instance = new Model();

    private Model(){
        for(int i=0; i<10;i++) {
            Recommend rc= new Recommend("id"+i, "title" + i,  "location"+i, "desc"+i, null);
            recommends.add(rc);
        }
    }

    public List<Recommend> getAllRec(){
        return recommends;
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
