package com.example.eat_it.model;

import android.media.Image;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Recommend implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;
    public String Ownerid;
    public String OwenrName;
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
