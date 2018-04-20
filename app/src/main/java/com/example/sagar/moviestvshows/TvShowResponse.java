package com.example.sagar.moviestvshows;

import java.util.ArrayList;

/**
 * Created by SAGAR on 16-04-2018.
 */

public class TvShowResponse {

    int page;
    int total_pages;
    int total_results;
    ArrayList<TvShows> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public ArrayList<TvShows> getResults() {
        return results;
    }

    public void setResults(ArrayList<TvShows> results) {
        this.results = results;
    }
}
