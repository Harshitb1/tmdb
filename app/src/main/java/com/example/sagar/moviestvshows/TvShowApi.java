package com.example.sagar.moviestvshows;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by SAGAR on 16-04-2018.
 */

public interface TvShowApi {

    @GET("tv/airing_today")
    Call<TvShowResponse> getAiringToday(@Query("api_key") String key);

    @GET("tv/on_the_air")
    Call<TvShowResponse> getOn_The_Air(@Query("api_key") String key);

    @GET("tv/popular")
    Call<TvShowResponse> getPopular(@Query("api_key") String key);

    @GET("tv/top_rated")
    Call<TvShowResponse> getTopRated(@Query("api_key") String key);

    @GET("tv/{tv_id}/videos")
    Call<MovieVideoResponse> getTvShowVideos(@Path("tv_id") String id,@Query("api_key") String key);

    @GET("tv/{tv_id}/similar")
    Call<TvShowResponse> getSimilarShows(@Path("tv_id") String id,@Query("api_key") String key);

    @GET("tv/{tv_id}/credits")
    Call<MovieCastResponse> getTvCast(@Path("tv_id") String id,@Query("api_key") String key);

    @GET("tv/{tv_id}")
    Call<TvShowDetails> getShowDetails(@Path("tv_id") String id,@Query("api_key") String key);

}
