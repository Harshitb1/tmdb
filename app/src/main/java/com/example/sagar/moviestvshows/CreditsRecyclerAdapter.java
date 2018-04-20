package com.example.sagar.moviestvshows;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SAGAR on 09-04-2018.
 */

public class CreditsRecyclerAdapter extends   RecyclerView.Adapter<CreditsRecyclerAdapter.CreditsViewHolder> {
    ArrayList<MovieCredits.Cast> casts;
    Context context;
    CreditsRecyclerAdapter.OnItemClickListener listener;
    //  WindowManager windowManager;

    int height,width;
    interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CreditsRecyclerAdapter(ArrayList<MovieCredits.Cast> casts, Context context, CreditsRecyclerAdapter.OnItemClickListener listener) {
        this.casts = casts;
        this.context = context;
        this.listener=listener;
        // this.windowManager=windowManager;
    }

    @Override
    public CreditsRecyclerAdapter.CreditsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.cast_layout,parent,false);
        CreditsRecyclerAdapter.CreditsViewHolder holder = new CreditsRecyclerAdapter.CreditsViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CreditsRecyclerAdapter.CreditsViewHolder holder, int position) {
        MovieCredits.Cast cast= casts.get(position);
        holder.username.setText(cast.getTitle());
        holder.characterName.setText("Char: "+ cast.getCharacter());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abcd","1");
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
        Picasso.get().load("http://image.tmdb.org/t/p/w780/"+cast.getPoster_path()).into(holder.avatar);
        //Glide.with(context).load("http://image.tmdb.org/t/p/w780/"+cast.getPoster_path()).into(holder.avatar);
    }


    @Override
    public int getItemCount() {
        return casts.size();
    }

    class CreditsViewHolder extends RecyclerView.ViewHolder{

        TextView username, characterName;
        CircleImageView avatar;
        View itemView;

        public CreditsViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            username = itemView.findViewById(R.id.tvCastName);
            characterName= itemView.findViewById(R.id.tvCharacter);
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
