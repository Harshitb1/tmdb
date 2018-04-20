package com.example.sagar.moviestvshows;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public interface OnFavoriteSelected{
        void onclick(Favourite favourite);
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
        favourites=(ArrayList<Favourite>)favoritesDAO.getAllFavorites();
        recyclerView= view.findViewById(R.id.favorites);

        adapter= new FavoritesRecyclerAdapter(favourites, getContext(), new FavoritesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mCallback.onclick(favourites.get(position));
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false));//LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

}
