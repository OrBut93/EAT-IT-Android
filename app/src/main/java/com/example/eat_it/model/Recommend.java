package com.example.eat_it.model;

import android.media.Image;
import android.widget.ImageView;

public class Recommend {

    public String id;
    public String title;
    public String location;
    public String description;
    public Image avatar;

    Recommend(String id,String title){
        this.title= title;
        this.id = id;
    }

}
