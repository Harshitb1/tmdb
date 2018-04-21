package com.example.sagar.moviestvshows;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAGAR on 20-04-2018.
 */


@Dao
public interface FavoritesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavourite(Favourite favourite);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavouriteList(ArrayList<Favourite> favourite);

    @Delete
    void deleteFavourite(Favourite favourite);

    @Query("Select * from Favourite")
    List<Favourite> getAllFavorites();

    @Query("Select * from Favourite where ismovie=1 and id=:t")
    Favourite checkMovie(int t);

    @Query("Select * from Favourite where ismovie=2 and id=:t")
    Favourite checkTvShow(int t);
}
