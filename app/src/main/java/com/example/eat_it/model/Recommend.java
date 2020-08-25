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
    public String id = "";
    public String Ownerid;
    public String OwenrName;
    public String title;
    public String location;
    public String description;
    public String avatar;
    long lastUpdated;


    public Recommend(String id, String title, String location, String description, String avatar){
        this.title= title;
        this.id = id;
        this.location= location;
        this.description= description;
        this.avatar = avatar;
    }
//    public Recommend() {
//    }

    ///////set

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setOwnerid(String name) {
        this.Ownerid = Ownerid;
    }

    public void setOwenrName(String OwenrName) {
        this.OwenrName = OwenrName;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

///// get

    @NonNull
    public String getId() {
        return id;
    }

    public String getOwnerid() {
        return Ownerid;
    }

    public String getOwenrName() {
        return OwenrName;
    }
    public String getTitle() {
        return title;
    }
    public String getLocation() {
        return location;
    }
    public String getDescription() {
        return description;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }


}
