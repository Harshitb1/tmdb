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

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SAGAR on 08-04-2018.
 */

public class CastRecyclerAdapter extends   RecyclerView.Adapter<CastRecyclerAdapter.CastViewHolder> {
    ArrayList<Cast> casts;
    Context context;
    CastRecyclerAdapter.OnItemClickListener listener;
  //  WindowManager windowManager;

    int height,width;
    interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CastRecyclerAdapter(ArrayList<Cast> casts, Context context, CastRecyclerAdapter.OnItemClickListener listener) {
        this.casts = casts;
        this.context = context;
        this.listener=listener;
       // this.windowManager=windowManager;
    }

    @Override
    public CastRecyclerAdapter.CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.cast_layout,parent,false);
        CastRecyclerAdapter.CastViewHolder holder = new CastRecyclerAdapter.CastViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CastRecyclerAdapter.CastViewHolder holder, int position) {
        Cast cast= casts.get(position);
        holder.username.setText(cast.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abcd","1");
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
        Picasso.get().load("http://image.tmdb.org/t/p/w780/"+cast.getProfile_path()).into(holder.avatar);
        //Glide.with(context).load("http://image.tmdb.org/t/p/w780/"+cast.getProfile_path()).into(holder.avatar);
    }


    @Override
    public int getItemCount() {
        return casts.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder{

        TextView username;
       CircleImageView avatar;
       View itemView;

        public CastViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            username = itemView.findViewById(R.id.tvCastName);
            avatar = itemView.findViewById(R.id.ciCast);
          //  getScreenSize();
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
