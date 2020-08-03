package com.example.eat_it.model;

import java.util.LinkedList;
import java.util.List;

public class Model {

    static final Model instance = new Model();

    private Model(){
    }

    List<Recommend> getAllRec(){
        return new LinkedList<Recommend>();
    }

    Recommend getRecommend(String id){

        return null;
    }


}
