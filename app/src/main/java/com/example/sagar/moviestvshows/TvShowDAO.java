package com.example.sagar.moviestvshows;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAGAR on 18-04-2018.
 */

@Dao
public interface TvShowDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShow(TvShows Show);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShowList(ArrayList<TvShows> showList);

    @Query("Select * from TvShows Where airing_today = 1")
    List<TvShows> getAiringToday();

    @Query("Select * from TvShows Where topRated = 1 Order By vote_average DESC")
    List<TvShows> getToprated();

    @Query("Select * from TvShows Where on_the_air = 1")
    List<TvShows> getOnTheAir();

    @Query("Select * from TvShows Where popular = 1")
    List<TvShows> getPopular();

    @Query("Select * from TvShows Where favourite = 1")
    List<TvShows> getFavourite();

}
