package com.example.sagar.moviestvshows;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAGAR on 11-04-2018.
 */


@Dao
public interface ActorDAO {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertActor(ActorDetails obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertActorList(ArrayList<ActorDetails> obj);

    @Query("select * from ActorDetails")
    List<ActorDetails> getAllDetails();
}
