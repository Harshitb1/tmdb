package com.example.sagar.moviestvshows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView,recyclerView1, recyclerView2, recyclerView3;
    ProgressBar progressBar;
    MovieRecyclerAdapter adapter, adapter1,adapter2,adapter3;
    ArrayList<Movies> popularmovies = new ArrayList<>();
    ArrayList<Movies> topratedmovies = new ArrayList<>();
    ArrayList<Movies> nowplayingmovies = new ArrayList<>();
    ArrayList<Movies> upcomingmovies = new ArrayList<>();
    WindowManager windowManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView2= findViewById(R.id.rvNowPlaying);
        recyclerView3 = findViewById(R.id.rvUpcomingMovies);
        progressBar = findViewById(R.id.progressBar);
        fetchPopularMovies();
        windowManager=getWindowManager();
        adapter= new MovieRecyclerAdapter(popularmovies, this, new MovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("a",position+"");
                Movies movie= popularmovies.get(position);
                Intent intent = new Intent(MainActivity.this,MovieProfileActivity.class);
                Bundle bundle= new Bundle();
                bundle.putInt("id",movie.getId());
                bundle.putString("name",movie.getTitle());
                bundle.putString("poster_path",movie.getPoster_path());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        },windowManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fetchTopratedMovies();

        adapter1= new MovieRecyclerAdapter(topratedmovies, this, new MovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("a",position+"");
                Movies movie= topratedmovies.get(position);
                Intent intent = new Intent(MainActivity.this,MovieProfileActivity.class);
                Bundle bundle= new Bundle();
                bundle.putString("name",movie.getTitle());
                bundle.putString("poster_path",movie.getPoster_path());
                bundle.putInt("id",movie.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        },windowManager);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView1.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        fetchNowPlayingMovies();
        adapter2= new MovieRecyclerAdapter(nowplayingmovies, this, new MovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("a",position+"");
                Movies movie= nowplayingmovies.get(position);
                Intent intent = new Intent(MainActivity.this,MovieProfileActivity.class);
                Bundle bundle= new Bundle();
                bundle.putString("name",movie.getTitle());
                bundle.putString("poster_path",movie.getPoster_path());
                bundle.putInt("id",movie.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        },windowManager);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        fetchUpcomingMovies();
        adapter3= new MovieRecyclerAdapter(upcomingmovies, this, new MovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("a",position+"");
                Movies movie= upcomingmovies.get(position);
                Intent intent = new Intent(MainActivity.this,MovieProfileActivity.class);
                Bundle bundle= new Bundle();
                bundle.putString("name",movie.getTitle());
                bundle.putString("poster_path",movie.getPoster_path());
                bundle.putInt("id",movie.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        },windowManager);
        recyclerView3.setAdapter(adapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView3.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
    }

    private void fetchUpcomingMovies() {
        recyclerView2.setVisibility(View.GONE);
        recyclerView3.setVisibility(View.GONE);
        recyclerView1.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> call= ApiClient.getInstance().getMovieApi().getUpcomingMovies("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1=response.body();
                ArrayList<Movies> moviesList = response1.getMovies();
                if(moviesList != null){
                    upcomingmovies.clear();
                    upcomingmovies.addAll(moviesList);
                    adapter3.notifyDataSetChanged();
                }
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView3.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.VISIBLE);
                recyclerView3.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

        });
    }

    private void fetchNowPlayingMovies() {
        recyclerView2.setVisibility(View.GONE);
        recyclerView1.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> call= ApiClient.getInstance().getMovieApi().getNowPlayingMovies("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1=response.body();
                ArrayList<Movies> moviesList = response1.getMovies();
                if(moviesList != null){
                    nowplayingmovies.clear();
                    nowplayingmovies.addAll(moviesList);
                    adapter2.notifyDataSetChanged();
                }
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

    }


    public void fetchTopratedMovies(){
        recyclerView1.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> call= ApiClient.getInstance().getMovieApi().getTopratedmovies("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1=response.body();
                ArrayList<Movies> moviesList = response1.getMovies();
                if(moviesList != null){
                    topratedmovies.clear();
                    topratedmovies.addAll(moviesList);
                    adapter1.notifyDataSetChanged();
                }
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

    }


    public void fetchPopularMovies(){
        recyclerView1.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> call= ApiClient.getInstance().getMovieApi().getPopularMovies("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1=response.body();
                ArrayList<Movies> moviesList = response1.getMovies();
                if(moviesList != null){
                    popularmovies.clear();
                    popularmovies.addAll(moviesList);
                    adapter.notifyDataSetChanged();
                }
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView1.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView1.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}
