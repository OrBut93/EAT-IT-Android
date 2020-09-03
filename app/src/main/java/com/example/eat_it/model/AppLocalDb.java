package com.example.eat_it.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.eat_it.MyApplication;

@Database(entities = {Recommend.class}, version = 2)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract RecommendDao recommendDao();
}
public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "Eat-It-Android.db")
                    .fallbackToDestructiveMigration()
                    .build();

}
