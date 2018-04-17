package com.example.sagar.moviestvshows;

import java.util.ArrayList;

/**
 * Created by SAGAR on 17-04-2018.
 */

public class TvShowDetails {

    class  Genres{
        int id;
        String name;
    }

    String backdrop_path;
    String first_air_date;
    ArrayList<TvShowDetails.Genres> genres;
    int id;
    String overview;
    String poster_path;
    String last_air_date;
    ArrayList<TvShowSeason> seasons;
    String name;
    boolean video;
    float vote_average;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public ArrayList<Genres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genres> genres) {
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public ArrayList<TvShowSeason> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<TvShowSeason> seasons) {
        this.seasons = seasons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }
}
