package com.example.sagar.moviestvshows;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SAGAR on 25-03-2018.
 */

public class ApiClient {

    MovieApi movieApi;

    static ApiClient client;


    private ApiClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi = retrofit.create(MovieApi.class);
    }

    public static ApiClient getInstance() {
        if(client == null){
            client = new ApiClient();
        }
        return client;
    }

    public MovieApi getMovieApi() {
        return movieApi;
    }
}
