package com.example.sagar.moviestvshows;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by SAGAR on 10-04-2018.
 */

@Database(entities = {Movies.class,TvShows.class,Favourite.class},version = 1,exportSchema = false)
public abstract class TmdbDatabase extends RoomDatabase{

    private static TmdbDatabase INSTANCE;

    public static TmdbDatabase getInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TmdbDatabase.class,"tmdb_databases")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    abstract MoviesDAO getMoviesDao();

    abstract TvShowDAO getTvShowDAO();

    abstract FavoritesDAO getFavoritesDAO();

}
