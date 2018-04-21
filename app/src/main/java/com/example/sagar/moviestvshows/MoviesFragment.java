package com.example.sagar.moviestvshows;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    RecyclerView recyclerView,recyclerView1, recyclerView2, recyclerView3;
    ProgressBar progressBar;
    MovieRecyclerAdapter adapter, adapter1,adapter2,adapter3;
    ArrayList<Movies> popularmovies = new ArrayList<>();
    ArrayList<Movies> topratedmovies = new ArrayList<>();
    ArrayList<Movies> nowplayingmovies = new ArrayList<>();
    ArrayList<Movies> upcomingmovies = new ArrayList<>();
    TextView t1,t2,t3,t4;
    MoviesDAO moviesDAO;
    FavoritesDAO favoritesDAO;
    WindowManager windowManager;
    MovieSelectedCallback mCallback;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (MovieSelectedCallback) context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException("Activity should implement MovieSelectedCallback");
        }

    }

    public  interface MovieSelectedCallback{
        void onMovieSelected(Movies movie);

    }

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
           View view= inflater.inflate(R.layout.fragment_movies, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView2= view.findViewById(R.id.rvNowPlaying);
        recyclerView3 = view.findViewById(R.id.rvUpcomingMovies);
        progressBar = view.findViewById(R.id.progressBar);
        t1=view.findViewById(R.id.textView);
        t2=view.findViewById(R.id.textView2);
        t3=view.findViewById(R.id.textView3);
        t4=view.findViewById(R.id.textView5);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        favoritesDAO=  TmdbDatabase.getInstance(getContext()).getFavoritesDAO();
        moviesDAO= TmdbDatabase.getInstance(getContext()).getMoviesDao();
        popularmovies=(ArrayList<Movies>)  moviesDAO.getPopularMovies();
        topratedmovies= (ArrayList<Movies>)moviesDAO.getTopRatedMovies();
        upcomingmovies= (ArrayList<Movies>)moviesDAO.getUpcomingMovies();
        nowplayingmovies= (ArrayList<Movies>)moviesDAO.getNowPlayingMovies();

        windowManager=(WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);


        adapter= new MovieRecyclerAdapter(popularmovies, getContext(), new MovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onFavoriteSelected(int position) {
                Movies movie= popularmovies.get(position);
                Favourite favourite = new Favourite(movie.getId(),movie.getTitle(),movie.getPoster_path(),1);
                int x=movie.getIsFavourite();
                if(x==0){
                    Toast.makeText(getContext(),"added to favorites",Toast.LENGTH_SHORT).show();
                    movie.setIsFavourite(1);
                    favoritesDAO.insertFavourite(favourite);
                }
                if(x==1){
                    Toast.makeText(getContext(),"removed from favorites",Toast.LENGTH_SHORT).show();
                    movie.setIsFavourite(0);
                    favoritesDAO.deleteFavourite(favourite);
                }
                moviesDAO.insertMovie(movie);
            }

            @Override
            public void onItemClick(int position) {
                Log.d("a",position+"");
                Movies movie= popularmovies.get(position);
                mCallback.onMovieSelected(movie);
            }
        },windowManager);

        adapter1= new MovieRecyclerAdapter(topratedmovies, getContext(), new MovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onFavoriteSelected(int position) {
                Movies movie= topratedmovies.get(position);
                int x=movie.getIsFavourite();
                Favourite favourite = new Favourite(movie.getId(),movie.getTitle(),movie.getPoster_path(),1);
                if(x==0){
                    Toast.makeText(getContext(),"added to favorites",Toast.LENGTH_SHORT).show();
                    movie.setIsFavourite(1);
                    favoritesDAO.insertFavourite(favourite);
                }
                if(x==1){
                    Toast.makeText(getContext(),"removed from favorites",Toast.LENGTH_SHORT).show();
                    movie.setIsFavourite(0);
                    favoritesDAO.deleteFavourite(favourite);
                }
                moviesDAO.insertMovie(movie);
            }
            @Override
            public void onItemClick(int position) {
                Log.d("a",position+"");
                Movies movie= topratedmovies.get(position);
                mCallback.onMovieSelected(movie);
            }
        },windowManager);

        adapter2= new MovieRecyclerAdapter(nowplayingmovies, getContext(), new MovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onFavoriteSelected(int position) {
                Movies movie= nowplayingmovies.get(position);
                int x=movie.getIsFavourite();
                Favourite favourite = new Favourite(movie.getId(),movie.getTitle(),movie.getPoster_path(),1);
                if(x==0){
                    Toast.makeText(getContext(),"added to favorites",Toast.LENGTH_SHORT).show();
                    movie.setIsFavourite(1);
                    favoritesDAO.insertFavourite(favourite);
                }
                if(x==1){
                    Toast.makeText(getContext(),"removed from favorites",Toast.LENGTH_SHORT).show();
                    movie.setIsFavourite(0);
                    favoritesDAO.deleteFavourite(favourite);
                }
                moviesDAO.insertMovie(movie);
            }
            @Override
            public void onItemClick(int position) {
                Log.d("a",position+"");
                Movies movie= nowplayingmovies.get(position);
                mCallback.onMovieSelected(movie);
            }
        },windowManager);

        adapter3= new MovieRecyclerAdapter(upcomingmovies, getContext(), new MovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onFavoriteSelected(int position) {
                Movies movie= upcomingmovies.get(position);
                int x=movie.getIsFavourite();
                Favourite favourite = new Favourite(movie.getId(),movie.getTitle(),movie.getPoster_path(),1);
                if(x==0){
                    Toast.makeText(getContext(),"added to favorites",Toast.LENGTH_SHORT).show();
                    movie.setIsFavourite(1);
                    favoritesDAO.insertFavourite(favourite);
                }
                if(x==1){
                    Toast.makeText(getContext(),"removed from favorites",Toast.LENGTH_SHORT).show();
                    movie.setIsFavourite(0);
                    favoritesDAO.deleteFavourite(favourite);
                }
                moviesDAO.insertMovie(movie);
            }
            @Override
            public void onItemClick(int position) {
                Log.d("a",position+"");
                Movies movie= upcomingmovies.get(position);
                mCallback.onMovieSelected(movie);
            }
        },windowManager);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularMovies();
                fetchTopratedMovies();
                fetchUpcomingMovies();
                fetchNowPlayingMovies();
                Toast.makeText(getContext(),"Movies Refreshed!",Toast.LENGTH_SHORT).show();
                if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);

            }
        });


        fetchPopularMovies();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fetchTopratedMovies();


        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView1.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        fetchNowPlayingMovies();

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        fetchUpcomingMovies();

        recyclerView3.setAdapter(adapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView3.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView3.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void fetchUpcomingMovies() {

//        recyclerView2.setVisibility(View.GONE);
//        recyclerView3.setVisibility(View.GONE);
//        recyclerView1.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);

        Call<MovieResponse> call= ApiClient.getInstance().getMovieApi().getUpcomingMovies("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1=response.body();
                ArrayList<Movies> moviesList = response1.getMovies();
                if(moviesList != null){
                    upcomingmovies.clear();

                    for(int i=0;i<moviesList.size();i++){
                        Movies m= moviesList.get(i);
                        m.setIsUpcoming(1);
                        Favourite f=favoritesDAO.checkMovie(m.id);
                        if(f!=null){
                            m.setIsFavourite(1);
                        }
                    }
                    upcomingmovies.addAll(moviesList);
                    adapter3.notifyDataSetChanged();
                    moviesDAO.insertMovieList(moviesList);

                }
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView3.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.VISIBLE);
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t4.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.VISIBLE);
                recyclerView3.setVisibility(View.VISIBLE);
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t4.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

        });
    }

    private void fetchNowPlayingMovies() {
//        recyclerView2.setVisibility(View.GONE);
//        recyclerView1.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> call= ApiClient.getInstance().getMovieApi().getNowPlayingMovies("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1=response.body();
                ArrayList<Movies> moviesList = response1.getMovies();
                if(moviesList != null){
                    nowplayingmovies.clear();


                    for(int i=0;i<moviesList.size();i++){
                        Movies m= moviesList.get(i);
                        m.setIsNowPlaying(1);
                        Favourite f=favoritesDAO.checkMovie(m.id);
                        if(f!=null){
                            m.setIsFavourite(1);
                        }
                    }
                    nowplayingmovies.addAll(moviesList);
                    adapter2.notifyDataSetChanged();
                    moviesDAO.insertMovieList(moviesList);
                }
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                recyclerView2.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                recyclerView2.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void fetchTopratedMovies(){
//        recyclerView1.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> call= ApiClient.getInstance().getMovieApi().getTopratedmovies("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1=response.body();
                ArrayList<Movies> moviesList = response1.getMovies();
                if(moviesList != null){
                    topratedmovies.clear();

                    for(int i=0;i<moviesList.size();i++){
                        Movies m= moviesList.get(i);
                        m.setIsTopRated(1);
                        Favourite f=favoritesDAO.checkMovie(m.id);
                        if(f!=null){
                            m.setIsFavourite(1);
                        }
                    }
                    topratedmovies.addAll(moviesList);
                    adapter1.notifyDataSetChanged();
                    moviesDAO.insertMovieList(moviesList);

                }
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void fetchPopularMovies(){
//        recyclerView1.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerView1.setVisibility(View.GONE);
        recyclerView2.setVisibility(View.GONE);
        recyclerView3.setVisibility(View.GONE);
        t1.setVisibility(View.GONE);
        t2.setVisibility(View.GONE);
        t3.setVisibility(View.GONE);
        t4.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> call= ApiClient.getInstance().getMovieApi().getPopularMovies("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1=response.body();
                ArrayList<Movies> moviesList = response1.getMovies();
                if(moviesList != null){
                }
//                recyclerView.setVisibility(View.VISIBLE);
//                recyclerView1.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
//                recyclerView.setVisibility(View.VISIBLE);
//                recyclerView1.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
