package com.example.sagar.moviestvshows;

/**
 * Created by SAGAR on 25-03-2018.
 */

public class Movies {

    int id;
    boolean video;
    String title;
    String poster_path;
    String overview;
    String release_date;
    String backdrop_path;
    String vote_average;
    String original_title;

    public void setId(int id) {
        this.id = id;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getVote_average() {
        return vote_average;
    }


    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }
}
