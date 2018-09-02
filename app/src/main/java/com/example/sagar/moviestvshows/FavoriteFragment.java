package com.example.sagar.moviestvshows;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    FavoritesDAO favoritesDAO;
    ArrayList<Favourite> favourites = new ArrayList<>();
    RecyclerView recyclerView;
    FavoritesRecyclerAdapter adapter;
    OnFavoriteSelected mCallback;
    SwipeRefreshLayout swipeRefreshLayout;

    public interface OnFavoriteSelected{
        void onclick(Favourite favourite);

    }

    @Override
    public void onStart() {
        super.onStart();
        favoritesDAO=  TmdbDatabase.getInstance(getContext()).getFavoritesDAO();
        favourites.clear();
        favourites.addAll((ArrayList<Favourite>)favoritesDAO.getAllFavorites());
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        favoritesDAO=  TmdbDatabase.getInstance(getContext()).getFavoritesDAO();
        favourites.clear();
        favourites.addAll((ArrayList<Favourite>)favoritesDAO.getAllFavorites());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FavoriteFragment.OnFavoriteSelected) context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException("Activity should implement FavoriteSelectedCallback");
        }

    }

    public FavoriteFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favorite, container, false);
        favoritesDAO=  TmdbDatabase.getInstance(getContext()).getFavoritesDAO();
        favourites.clear();
        favourites.addAll((ArrayList<Favourite>)favoritesDAO.getAllFavorites());
        recyclerView= view.findViewById(R.id.favorites);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                favourites.clear();
                favourites.addAll(favoritesDAO.getAllFavorites());
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"Favorites Refreshed!",Toast.LENGTH_SHORT).show();
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);

            }
        });


        adapter= new FavoritesRecyclerAdapter(favourites, getContext(), new FavoritesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mCallback.onclick(favourites.get(position));
            }

            @Override
            public void onFavoriteClick(int position) {
               Favourite favourite= favourites.get(position);
//             Favourite favourite = new Favourite(movie.getId(),movie.getTitle(),movie.getPoster_path(),1);
                int is_movie=favourite.getIsmovie();
                if(is_movie==1){
                    MoviesDAO moviesDAO= TmdbDatabase.getInstance(getContext()).getMoviesDao();
                    Movies movie=moviesDAO.getParticularMovie(favourite.getId());
                    movie.setIsFavourite(0);
                    moviesDAO.insertMovie(movie);
                }
                else{
                    TvShowDAO tvShowDAO =TmdbDatabase.getInstance(getContext()).getTvShowDAO();
                    TvShows tvShows=tvShowDAO.getParticularTvShow(favourite.getId());
                    tvShows.setFavourite(0);
                    tvShowDAO.insertTvShow(tvShows);
                }
                    Toast.makeText(getContext(),"removed from favorites",Toast.LENGTH_SHORT).show();
                    favoritesDAO.deleteFavourite(favourite);
                    favourites.remove(position);
                    adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false));//LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

}
