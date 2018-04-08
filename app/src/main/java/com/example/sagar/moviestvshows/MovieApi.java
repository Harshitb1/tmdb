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
    Call<MovieResponse> getPopularMovies(@Query("api_key") String key);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopratedmovies(@Query("api_key") String key);

    @GET("movie/{id}")
    Call<MovieDetails> getMovieDetails(@Path("id") int id, @Query("api_key") String key);

    @GET("movie/{movie_id}/credits")
    Call<MovieCastResponse> getCastDetails(@Path("movie_id") int id, @Query("api_key") String key);
}