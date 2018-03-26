package com.example.sagar.moviestvshows;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by SAGAR on 26-03-2018.
 */

public class MovieResponse {


    int page;
    int total_results;
    int total_pages;
    @SerializedName("results")
    ArrayList<Movies> movies;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public MovieResponse(int page, int total_results, int total_pages, ArrayList<Movies> movies) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.movies = movies;
    }

    public ArrayList<Movies> getMovies() {

        return movies;
    }

    public void setMovies(ArrayList<Movies> movies) {
        this.movies = movies;
    }

}
