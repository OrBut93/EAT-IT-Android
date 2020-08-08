package com.example.eat_it.model;

import android.media.Image;
import android.widget.ImageView;

public class Recommend {

    public String id;
    public String title;
    public String location;
    public String description;
    public String avatar;

    Recommend(String id,String title,String location, String description, String avatar){
        this.title= title;
        this.id = id;
        this.location= location;
        this.description= description;
        this.avatar = avatar;
    }

}
