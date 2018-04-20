package com.example.sagar.moviestvshows;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by SAGAR on 19-04-2018.
 */

@Entity
public class Favourite {
    @PrimaryKey
    int id;
    String name;
    String poster_path;
    int ismovie;

    public int getIsmovie() {
        return ismovie;
    }

    public void setIsmovie(int ismovie) {
        this.ismovie = ismovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Favourite(int id, String name, String poster_path, int ismovie) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.ismovie=ismovie;
    }
}
