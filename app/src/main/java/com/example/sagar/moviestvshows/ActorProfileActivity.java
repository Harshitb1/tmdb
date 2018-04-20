package com.example.sagar.moviestvshows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActorProfileActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name,birth,biography;
    ArrayList<Cast> casts;
    RecyclerView recyclerView;
    CreditsRecyclerAdapter adapter;
    ArrayList<MovieCredits.Cast> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_profile);
        imageView= findViewById(R.id.imActor);
        name= findViewById(R.id.tvName);
        birth= findViewById(R.id.tvBirth);
        biography=findViewById(R.id.overview);
        casts= new ArrayList<>();
        list= new ArrayList<>();
        recyclerView= findViewById(R.id.rvCredits);
        Intent intent = getIntent();
        Bundle bundle= intent.getExtras();
        if(bundle!=null){
          int id=bundle.getInt("id");
          fetchActorDetails(id);
          fetchMovieCredits(id);
        }
        adapter= new CreditsRecyclerAdapter(list, this, new CreditsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MovieCredits.Cast obj = list.get(position);
                Intent intent = new Intent(ActorProfileActivity.this, MovieProfileActivity.class);
                Bundle bundle= new Bundle();
                bundle.putInt("id",obj.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void fetchMovieCredits(int id) {
        Call<MovieCredits> call= ApiClient.getInstance().getMovieApi().getMovieCredits(id,"9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieCredits>() {
            @Override
            public void onResponse(Call<MovieCredits> call, Response<MovieCredits> response) {
                MovieCredits details= response.body();
                ArrayList<MovieCredits.Cast> arrayList=details.getCast();
                if(arrayList!=null){
                    list.clear();
                    list.addAll(arrayList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieCredits> call, Throwable t) {
                Toast.makeText(ActorProfileActivity.this,"failed Credits",Toast.LENGTH_SHORT);
            }
        });
    }

    private void fetchActorDetails(int id) {
        Call<ActorDetails> call= ApiClient.getInstance().getMovieApi().getActorDetails(id,"9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<ActorDetails>() {
            @Override
            public void onResponse(Call<ActorDetails> call, Response<ActorDetails> response) {
                ActorDetails details= response.body();
                if(details==null){
                    Log.d("111","null detail");
                }
                name.setText(details.getName());
                biography.setText(details.getBiography());
                birth.setText(details.getPlace_of_birth());
                //TmdbDatabase.getInstance(ActorProfileActivity.this).getActorDao().insertActor(details);
                Picasso.get().load("http://image.tmdb.org/t/p/w780/"+details.getProfile_path()).into(imageView);
                //Glide.with(ActorProfileActivity.this).load("http://image.tmdb.org/t/p/w780/"+details.getProfile_path()).into(imageView);
            }

            @Override
            public void onFailure(Call<ActorDetails> call, Throwable t) {
                Toast.makeText(ActorProfileActivity.this,"failed",Toast.LENGTH_SHORT);
            }
        });
    }
}
