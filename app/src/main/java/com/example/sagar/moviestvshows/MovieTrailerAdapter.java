package com.example.sagar.moviestvshows;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SAGAR on 14-04-2018.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {

    interface OnItemClickListener{
        void onItemClick(int position);
    }

    Context context;
    int height,width;
    WindowManager windowManager;
    ArrayList<MovieVideoResponse.Trailer> trailers;
    MovieTrailerAdapter.OnItemClickListener listener;

    public MovieTrailerAdapter(Context context, WindowManager windowManager, ArrayList<MovieVideoResponse.Trailer> trailers, OnItemClickListener listener) {
        this.context = context;
        this.windowManager = windowManager;
         this.trailers= trailers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieTrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.row_user,parent,false);
        MovieTrailerAdapter.ViewHolder holder = new MovieTrailerAdapter.ViewHolder(itemView);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MovieTrailerAdapter.ViewHolder holder, int position) {

        MovieVideoResponse.Trailer trailer= trailers.get(position);
        holder.username.setText(trailer.getType());
        holder.fav.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });

        Picasso.get().load("https://img.youtube.com/vi/"+trailer.getKey()+"/mqdefault.jpg").resize(width,0).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        ImageView avatar;
        ImageView fav;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            username = itemView.findViewById(R.id.username);
            avatar = itemView.findViewById(R.id.avatar);
            fav= itemView.findViewById(R.id.fav);
            getScreenSize();
        }
        public void getScreenSize(){

            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            width = displayMetrics.widthPixels;
        }
    }
}
