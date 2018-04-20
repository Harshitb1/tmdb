package com.example.sagar.moviestvshows;

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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SAGAR on 16-04-2018.
 */

public class TvShowRecyclerAdapter extends RecyclerView.Adapter<TvShowRecyclerAdapter.TvShowViewHolder> {

    ArrayList<TvShows> tvShowslist;
    Context context;
    TvShowRecyclerAdapter.OnItemClickListener listener;
    WindowManager windowManager;
    int height,width;

    interface OnItemClickListener {
        void onItemClick(int position);
        void onFavoriteSelected(int position);

    }

    public TvShowRecyclerAdapter(ArrayList<TvShows> tvShowslist, Context context, TvShowRecyclerAdapter.OnItemClickListener listener, WindowManager windowManager) {
        this.tvShowslist=tvShowslist;
        this.context = context;
        this.listener=listener;
        this.windowManager=windowManager;
    }

    @Override
    public TvShowRecyclerAdapter.TvShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.row_user,parent,false);
        TvShowRecyclerAdapter.TvShowViewHolder holder = new TvShowRecyclerAdapter.TvShowViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TvShowRecyclerAdapter.TvShowViewHolder holder, int position) {
        TvShows show= tvShowslist.get(position);
        holder.username.setText(show.getName());
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFavoriteSelected(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abcd","1");
                listener.onItemClick(holder.getAdapterPosition());
            }
        });

        Picasso.get().load("http://image.tmdb.org/t/p/w780/"+show.getBackdrop_path()).resize(width,0).into(holder.avatar);
        // Glide.with(context).load("http://image.tmdb.org/t/p/w780/"+movie.getBackdrop_path()).override(width,width/2).into(holder.avatar);
        Log.d("121","http://image.tmdb.org/t/p/w780/"+show.getBackdrop_path());
    }


    @Override
    public int getItemCount() {
        return tvShowslist.size();
    }


    class TvShowViewHolder extends RecyclerView.ViewHolder {


        TextView username;
        ImageView avatar;
        ImageView fav;
        View itemView;

        public TvShowViewHolder(View itemView) {
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
