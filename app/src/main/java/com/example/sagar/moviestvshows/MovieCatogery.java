package com.example.sagar.moviestvshows;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;

/**
 * Created by SAGAR on 11-04-2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = Movies.class,parentColumns = "id",childColumns = "movie_id"))
public class MovieCatogery {


    //5 favourites
    @PrimaryKey(autoGenerate = true)
    @NonNull

    int id;
    int catogery;
    int movie_id;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getCatogery() {
        return catogery;
    }

    public void setCatogery(int catogery) {
        this.catogery = catogery;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public MovieCatogery(@NonNull int id, int catogery, int movie_id) {

        this.id = id;
        this.catogery = catogery;
        this.movie_id = movie_id;
    }
}
