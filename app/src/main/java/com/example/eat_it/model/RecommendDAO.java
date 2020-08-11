package com.example.eat_it.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
interface RecommendDao {

    @Query("select * from Recommend")
    LiveData<List<Recommend>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Recommend... recommends);

    @Delete
    void delete(Recommend recommend);
}

