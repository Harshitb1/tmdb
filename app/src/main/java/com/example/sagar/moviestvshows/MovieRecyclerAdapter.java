package com.example.sagar.moviestvshows;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SAGAR on 25-03-2018.
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    ArrayList<Movies> movies;
    Context context;
    OnItemClickListener listener;
    WindowManager windowManager;

    int height,width;
    interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MovieRecyclerAdapter(ArrayList<Movies> movies, Context context, OnItemClickListener listener,WindowManager windowManager) {
        this.movies = movies;
        this.context = context;
        this.listener=listener;
        this.windowManager=windowManager;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.row_user,parent,false);
        MovieViewHolder holder = new MovieViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        Movies movie= movies.get(position);
        holder.username.setText(movie.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abcd","1");
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
        Picasso.get().load("http://image.tmdb.org/t/p/w780/"+movie.getBackdrop_path()).resize(width,0).into(holder.avatar);
       // Glide.with(context).load("http://image.tmdb.org/t/p/w780/"+movie.getBackdrop_path()).override(width,width/2).into(holder.avatar);
       Log.d("121","http://image.tmdb.org/t/p/w780/"+movie.getBackdrop_path());
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        ImageView avatar;
        View itemView;


        public MovieViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            username = itemView.findViewById(R.id.username);
            avatar = itemView.findViewById(R.id.avatar);
            getScreenSize();
        }
    }

    public void getScreenSize(){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }
}
