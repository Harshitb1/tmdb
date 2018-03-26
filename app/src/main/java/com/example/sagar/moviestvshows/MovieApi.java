package com.example.sagar.moviestvshows;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by SAGAR on 25-03-2018.
 */

public interface MovieApi {

   @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String key);
}
