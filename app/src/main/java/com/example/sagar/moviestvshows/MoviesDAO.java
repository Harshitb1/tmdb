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
 * Created by SAGAR on 10-04-2018.
 */

@Dao
public interface MoviesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movies movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieList(ArrayList<Movies> movies);

    @Delete
    void deleteMovie(Movies movie);

    @Update
    void UpdateMovie(Movies movie);

    @Query("Select * from Movies Where id = :t")
    Movies getParticularMovie(int t);

    @Query("Select * from Movies Where isPopular = 1")
    List<Movies> getPopularMovies();

    @Query("Select * from Movies Where isTopRated = 1 Order By vote_average DESC")
    List<Movies> getTopRatedMovies();

    @Query("Select * from Movies Where isNowPlaying = 1")
    List<Movies> getNowPlayingMovies();

    @Query("Select * from Movies Where isUpcoming = 1")
    List<Movies> getUpcomingMovies();

    @Query("Select * from Movies Where isFavourite = 1")
    List<Movies> getFavouriteMovies();

    // @Query("Select * from Movies WHERE ")

}
