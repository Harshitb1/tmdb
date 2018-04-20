package com.example.sagar.moviestvshows;


import android.content.Context;
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
public class TvFragment extends Fragment {


    RecyclerView recyclerView,recyclerView1, recyclerView2, recyclerView3;
    ProgressBar progressBar;
    TvShowRecyclerAdapter adapter, adapter1,adapter2,adapter3;
    ArrayList<TvShows> airingtoday = new ArrayList<>();
    ArrayList<TvShows> ontheair = new ArrayList<>();
    ArrayList<TvShows> popular = new ArrayList<>();
    ArrayList<TvShows> toprated = new ArrayList<>();
    TvShowDAO tvShowDAO ;
    FavoritesDAO favoritesDAO;
    SwipeRefreshLayout swipeRefreshLayout;
    WindowManager windowManager;
    TvFragment.TvShowSelectedCallback mCallback;
    TextView t1,t2;

    public  interface TvShowSelectedCallback{
        void onTvShowSelected(TvShows show);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (TvFragment.TvShowSelectedCallback) context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException("Activity should implement TvShowSelectedCallback");
        }

    }
    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tv, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView2= view.findViewById(R.id.rvNowPlaying);
        recyclerView3 = view.findViewById(R.id.rvUpcomingMovies);
        progressBar = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        t1= view.findViewById(R.id.textView3);
        t2=view.findViewById(R.id.textView5);
        windowManager=(WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        favoritesDAO=TmdbDatabase.getInstance(getContext()).getFavoritesDAO();
        tvShowDAO=TmdbDatabase.getInstance(getContext()).getTvShowDAO();
        airingtoday=(ArrayList<TvShows>) tvShowDAO.getAiringToday();
        popular=(ArrayList<TvShows>) tvShowDAO.getPopular();
        toprated=(ArrayList<TvShows>) tvShowDAO.getToprated();
        ontheair=(ArrayList<TvShows>) tvShowDAO.getOnTheAir();

        adapter= new TvShowRecyclerAdapter(popular, getContext(), new TvShowRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TvShows show= popular.get(position);
                mCallback.onTvShowSelected(show);
            }

            @Override
            public void onFavoriteSelected(int position) {
                TvShows show= popular.get(position);
                Favourite favourite = new Favourite(show.getId(),show.getName(),show.getPoster_path(),0);
                int x=show.getFavourite();
                if(x==0){
                    Toast.makeText(getContext(),"added to favorites",Toast.LENGTH_SHORT).show();
                    show.setFavourite(1);
                    favoritesDAO.insertFavourite(favourite);
                }
                if(x==1){
                    Toast.makeText(getContext(),"removed from favorites",Toast.LENGTH_SHORT).show();
                    show.setFavourite(0);
                    favoritesDAO.deleteFavourite(favourite);
                }
                tvShowDAO.insertTvShow(show);
            }

        },windowManager);

        adapter1= new TvShowRecyclerAdapter(toprated, getContext(), new TvShowRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TvShows show= toprated.get(position);
                mCallback.onTvShowSelected(show);
            }

            @Override
            public void onFavoriteSelected(int position) {
                TvShows show= toprated.get(position);
                Favourite favourite = new Favourite(show.getId(),show.getName(),show.getPoster_path(),0);
                int x=show.getFavourite();
                if(x==0){
                    Toast.makeText(getContext(),"added to favorites",Toast.LENGTH_SHORT).show();
                    show.setFavourite(1);
                    favoritesDAO.insertFavourite(favourite);
                }
                if(x==1){
                    Toast.makeText(getContext(),"removed from favorites",Toast.LENGTH_SHORT).show();
                    show.setFavourite(0);
                    favoritesDAO.deleteFavourite(favourite);
                }
                tvShowDAO.insertTvShow(show);
            }
        },windowManager);

        t1.setText("On The Air");
        adapter2= new TvShowRecyclerAdapter(ontheair, getContext(), new TvShowRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TvShows show= ontheair.get(position);
                mCallback.onTvShowSelected(show);
            }

            @Override
            public void onFavoriteSelected(int position) {
                TvShows show= ontheair.get(position);
                Favourite favourite = new Favourite(show.getId(),show.getName(),show.getPoster_path(),0);
                int x=show.getFavourite();
                if(x==0){
                    Toast.makeText(getContext(),"added to favorites",Toast.LENGTH_SHORT).show();
                    show.setFavourite(1);
                    favoritesDAO.insertFavourite(favourite);
                }
                if(x==1){
                    Toast.makeText(getContext(),"removed from favorites",Toast.LENGTH_SHORT).show();
                    show.setFavourite(0);
                    favoritesDAO.deleteFavourite(favourite);
                }
                tvShowDAO.insertTvShow(show);
            }
        },windowManager);

        t2.setText("Airing Today");
        adapter3= new TvShowRecyclerAdapter(airingtoday, getContext(), new TvShowRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TvShows show= airingtoday.get(position);
                mCallback.onTvShowSelected(show);
            }

            @Override
            public void onFavoriteSelected(int position) {
                TvShows show= airingtoday.get(position);
                Favourite favourite = new Favourite(show.getId(),show.getName(),show.getPoster_path(),0);
                int x=show.getFavourite();
                if(x==0){
                    Toast.makeText(getContext(),"added to favorites",Toast.LENGTH_SHORT).show();
                    show.setFavourite(1);
                    favoritesDAO.insertFavourite(favourite);
                }
                if(x==1){
                    Toast.makeText(getContext(),"removed from favorites",Toast.LENGTH_SHORT).show();
                    show.setFavourite(0);
                    favoritesDAO.deleteFavourite(favourite);
                }
                tvShowDAO.insertTvShow(show);
            }
        },windowManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopular();
                fetchToprated();
                fetchOnTheAir();
                fetchAiringToday();
                Toast.makeText(getContext(),"Tv Shows Refreshed!",Toast.LENGTH_SHORT).show();
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }
        });

        fetchPopular();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fetchToprated();

        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView1.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        fetchOnTheAir();

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        fetchAiringToday();

        recyclerView3.setAdapter(adapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView3.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    private void fetchAiringToday() {

        Call<TvShowResponse> call= ApiClient.getInstance().getTvShowApi().getAiringToday("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse response1 = response.body();
                ArrayList<TvShows> moviesList = response1.getResults();
                if (moviesList != null) {
                    airingtoday.clear();

                    Log.d("aaa",moviesList.get(0).getName());
                    for(int i=0;i<moviesList.size();i++){
                        TvShows m= moviesList.get(i);
                        m.setAiring_today(1);
                    }
                    airingtoday.addAll(moviesList);
                    adapter3.notifyDataSetChanged();
                    tvShowDAO.insertTvShowList(airingtoday);
                    //  moviesDAO.insertMovieList(moviesList);

                }
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void fetchOnTheAir() {
        Call<TvShowResponse> call= ApiClient.getInstance().getTvShowApi().getOn_The_Air("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse response1 = response.body();
                ArrayList<TvShows> moviesList = response1.getResults();
                if (moviesList != null) {
                    ontheair.clear();

                    Log.d("aaa",moviesList.get(0).getName());

                    for(int i=0;i<moviesList.size();i++){
                        TvShows m= moviesList.get(i);
                        m.setOn_the_air(1);
                    }
                    ontheair.addAll(moviesList);
                    adapter2.notifyDataSetChanged();
                    tvShowDAO.insertTvShowList(ontheair);

                    //  moviesDAO.insertMovieList(moviesList);

                }
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void fetchToprated() {
        Call<TvShowResponse> call= ApiClient.getInstance().getTvShowApi().getTopRated("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse response1 = response.body();
                ArrayList<TvShows> moviesList = response1.getResults();
                if (moviesList != null) {
                    toprated.clear();
                    Log.d("aaa",moviesList.get(0).getName());

                    for(int i=0;i<moviesList.size();i++){
                        TvShows m= moviesList.get(i);
                        m.setTopRated(1);
                    }
                    toprated.addAll(moviesList);
                    adapter1.notifyDataSetChanged();
                    tvShowDAO.insertTvShowList(toprated);

                    //  moviesDAO.insertMovieList(moviesList);

                }
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void fetchPopular() {

        //        recyclerView1.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
        Call<TvShowResponse> call= ApiClient.getInstance().getTvShowApi().getPopular("9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse response1 = response.body();
                ArrayList<TvShows> moviesList = response1.getResults();
                if (moviesList != null) {
                    popular.clear();
                    Log.d("aaa",moviesList.get(0).getName());

                    for(int i=0;i<moviesList.size();i++){
                        TvShows m= moviesList.get(i);
                        m.setPopular(1);
                    }
                    popular.addAll(moviesList);
                    adapter.notifyDataSetChanged();
                    tvShowDAO.insertTvShowList(popular);

                    //  moviesDAO.insertMovieList(moviesList);

                }
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                recyclerView1.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
