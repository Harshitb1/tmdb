package com.example.sagar.moviestvshows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowProfileActivity extends AppCompatActivity {

    TextView textView, genres, star, overview;
    ImageView imageView, imageView1, imageViewStar;
    Bundle bundle;
    String title, posterpath;
    int height, width;
    private Call<MovieCastResponse> call;
    ArrayList<Cast> casts;
    ArrayList<TvShows> similar;
    ArrayList<MovieVideoResponse.Trailer> trailers = new ArrayList<>();
    CastRecyclerAdapter adapter;
    TvShowRecyclerAdapter2 adapter1;
    RecyclerView recyclerView, recyclerView1, recyclerView2;
    MovieTrailerAdapter movieTrailerAdapter;
    FavoritesDAO favoritesDAO;
    TvShowDAO tvShowDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_profile);
        textView = findViewById(R.id.tv);
        imageView = findViewById(R.id.im);
        imageView1 = findViewById(R.id.im1);
        genres = findViewById(R.id.tvGenres);
        imageViewStar = findViewById(R.id.imageViewStar);
        star = findViewById(R.id.tvRating);
        overview = findViewById(R.id.tvOverview);
        recyclerView = findViewById(R.id.rvCast);
        recyclerView1 = findViewById(R.id.rvSimilarMovies);
        recyclerView2 = findViewById(R.id.rvTrailers);
        favoritesDAO=TmdbDatabase.getInstance(this).getFavoritesDAO();
        tvShowDAO=TmdbDatabase.getInstance(this).getTvShowDAO();
        final Intent intent = getIntent();
        bundle = intent.getExtras();
        casts = new ArrayList<Cast>();
        similar = new ArrayList<>();

        if (bundle != null) {
            int id = bundle.getInt("id");
            fetchDetails(id);
            fetchCasts(id);
            fetchTraiers(id);
            fetchSimilarTvShows(id);
        }

        adapter = new CastRecyclerAdapter(casts, this, new CastRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Cast cast = casts.get(position);
                Intent intent = new Intent(TvShowProfileActivity.this, ActorProfileActivity.class);
                Bundle bundle = new Bundle();
                int id = cast.getId();
                Log.d("id11", id + "");
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        WindowManager windowManager = getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        if(similar==null){
            Log.d("aab","similar tv null");
        }

        adapter1 = new TvShowRecyclerAdapter2(similar, this, new TvShowRecyclerAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TvShows show = similar.get(position);
                Intent intent = new Intent(TvShowProfileActivity.this, TvShowProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", show.getId());
                bundle.putString("name", show.getName());
                bundle.putString("poster_path", show.getPoster_path());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onFavoriteSelected(int position) {
                TvShows show= similar.get(position);
                Favourite favourite = new Favourite(show.getId(),show.getName(),show.getPoster_path(),2);
                int x=show.getFavourite();
                if(x==0){
                    Toast.makeText(TvShowProfileActivity.this,"added to favorites",Toast.LENGTH_SHORT).show();
                    show.setFavourite(1);
                    favoritesDAO.insertFavourite(favourite);
                }
                if(x==1){
                    Toast.makeText(TvShowProfileActivity.this,"removed from favorites",Toast.LENGTH_SHORT).show();
                    show.setFavourite(0);
                    favoritesDAO.deleteFavourite(favourite);
                }
                tvShowDAO.insertTvShow(show);
            }
        });
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        movieTrailerAdapter = new MovieTrailerAdapter(this, getWindowManager(), trailers, new MovieTrailerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MovieVideoResponse.Trailer trail = trailers.get(position);
                String trailkey = trail.getKey();
                Intent intent1 = new Intent(TvShowProfileActivity.this, VideoPlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", trailkey);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });
        recyclerView2.setAdapter(movieTrailerAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

    }

    private void fetchTraiers(int id) {
        Call<MovieVideoResponse> call = ApiClient.getInstance().getTvShowApi().getTvShowVideos(id+"", "9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieVideoResponse>() {
            @Override
            public void onResponse(Call<MovieVideoResponse> call, Response<MovieVideoResponse> response) {
                MovieVideoResponse response1 = response.body();
                ArrayList<MovieVideoResponse.Trailer> list = response1.getTrailers();
                if (list != null) {
                    trailers.clear();
                    trailers.addAll(list);
                    movieTrailerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieVideoResponse> call, Throwable t) {

                Toast.makeText(TvShowProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void  fetchSimilarTvShows(int id) {

        Call<TvShowResponse> call = ApiClient.getInstance().getTvShowApi().getSimilarShows(id + "", "9e88cc754362f676e652e8856be5d62d");

        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {

                TvShowResponse response1 = response.body();
                ArrayList<TvShows> moviesList = response1.getResults();
                if (moviesList != null) {
                    similar.clear();
                    for(int i=0;i<moviesList.size();i++){
                        TvShows m= moviesList.get(i);
                        Favourite f=favoritesDAO.checkMovie(m.id);
                        if(f!=null){
                            m.setFavourite(1);
                            tvShowDAO.insertTvShow(m);
                        }
                    }
                    similar.addAll(moviesList);
                    adapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Toast.makeText(TvShowProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCasts(int id) {
        Call<MovieCastResponse> call = ApiClient.getInstance().getTvShowApi().getTvCast(id+"", "9e88cc754362f676e652e8856be5d62d");
        Log.d("abab", "fetching casts");
        call.enqueue(new Callback<MovieCastResponse>() {

            @Override
            public void onResponse(Call<MovieCastResponse> call, Response<MovieCastResponse> response) {
                Log.d("abab", "fetching response");
                MovieCastResponse castResponse = response.body();

                ArrayList<Cast> list = castResponse.getCast();
                if (list != null) {
                    casts.clear();
                    casts.addAll(list);

                    adapter.notifyDataSetChanged();
                }
               // Log.d("abab", casts.get(0).getName());

            }

            @Override
            public void onFailure(Call<MovieCastResponse> call, Throwable t) {
                Log.d("abab", "failure");
                Toast.makeText(TvShowProfileActivity.this, "cast error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchDetails(int id) {
        Call<TvShowDetails> call = ApiClient.getInstance().getTvShowApi().getShowDetails(id+"", "9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<TvShowDetails>() {
            @Override
            public void onResponse(Call<TvShowDetails> call, Response<TvShowDetails> response) {
                TvShowDetails details = response.body();
                textView.setText(details.getName());
                star.setText(details.getVote_average() + "");
                overview.setText("OVERVIEW" + "\n" + details.getOverview());
                String genre = "";
                for (int i = 0; i < details.getGenres().size(); i++) {
                    genre = genre + "  " + details.getGenres().get(i).name;
                }
                genres.setText(genre);
                Picasso.get().load("http://image.tmdb.org/t/p/original/" + details.getPoster_path()).into(imageView1);
                //Glide.with(MovieProfileActivity.this).load("http://image.tmdb.org/t/p/original/"+movieDetails.getPoster_path()).into(imageView1);
                Picasso.get().load("http://image.tmdb.org/t/p/w780/" + details.getBackdrop_path()).resize(width, 0).into(imageView);
                // Glide.with(MovieProfileActivity.this).load("http://image.tmdb.org/t/p/w780/"+movieDetails.getBackdrop_path()).override(width,width/2).into(imageView);
            }

            @Override
            public void onFailure(Call<TvShowDetails> call, Throwable t) {
                Toast.makeText(TvShowProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}

