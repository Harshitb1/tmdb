package com.example.sagar.moviestvshows;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SAGAR on 20-04-2018.
 */

public class FavoritesRecyclerAdapter extends RecyclerView.Adapter<FavoritesRecyclerAdapter.FavoritesViewHolder> {

    ArrayList<Favourite> movies;
    Context context;
    FavoritesRecyclerAdapter.OnItemClickListener listener;
    WindowManager windowManager;

    int height,width;
    interface OnItemClickListener {
        void onItemClick(int position);
    }

    public FavoritesRecyclerAdapter(ArrayList<Favourite> movies, Context context, FavoritesRecyclerAdapter.OnItemClickListener listener) {
        this.movies = movies;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public FavoritesRecyclerAdapter.FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.movie_small_layout,parent,false);
        FavoritesRecyclerAdapter.FavoritesViewHolder holder = new FavoritesRecyclerAdapter.FavoritesViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final FavoritesRecyclerAdapter.FavoritesViewHolder holder, int position) {

        Favourite movie= movies.get(position);
        // holder.username.setText(movie.getTitle());
        holder.fav.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abcd","1");
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
        Picasso.get().load("http://image.tmdb.org/t/p/w342/"+movie.getPoster_path()).into(holder.avatar);
        // Glide.with(context).load("http://image.tmdb.org/t/p/w780/"+movie.getBackdrop_path()).override(width,width/2).into(holder.avatar);
        //Log.d("121","http://image.tmdb.org/t/p/w185/"+movie.getBackdrop_path());
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        ImageView avatar;
        ImageView fav;
        View itemView;


        public FavoritesViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            fav= itemView.findViewById(R.id.fav);
            // username = itemView.findViewById(R.id.username);
            avatar = itemView.findViewById(R.id.avatar);
            // getScreenSize();
        }
    }

//    public void getScreenSize(){
//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        height = displayMetrics.heightPixels;
//        width = displayMetrics.widthPixels;
//    }
}
