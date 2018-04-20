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

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieProfileActivity extends AppCompatActivity {
    TextView textView, genres, star, overview;
    ImageView imageView, imageView1, imageViewStar;
    Bundle bundle;
    String title, posterpath;
    int height, width;
    private Call<MovieCastResponse> call;
    ArrayList<Cast> casts;
    ArrayList<Movies> similarmovies;
    ArrayList<MovieVideoResponse.Trailer> trailers = new ArrayList<>();
    CastRecyclerAdapter adapter;
    MovieRecyclerAdapter2 adapter1;
    RecyclerView recyclerView, recyclerView1, recyclerView2;
    MovieTrailerAdapter movieTrailerAdapter;
    MoviesDAO moviesDAO;
    FavoritesDAO favoritesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        final Intent intent = getIntent();
        bundle = intent.getExtras();
        casts = new ArrayList<Cast>();
        similarmovies = new ArrayList<>();
        favoritesDAO=  TmdbDatabase.getInstance(this).getFavoritesDAO();
        moviesDAO= TmdbDatabase.getInstance(this).getMoviesDao();

        if (bundle != null) {
            int id = bundle.getInt("id");
            fetchDetails(id);
            fetchCasts(id);
            fetchTraiers(id);
            fetchSimilarMovies(id);
        }

        adapter = new CastRecyclerAdapter(casts, this, new CastRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Cast cast = casts.get(position);
                Intent intent = new Intent(MovieProfileActivity.this, ActorProfileActivity.class);
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

        adapter1 = new MovieRecyclerAdapter2(similarmovies, this, new MovieRecyclerAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Movies movie = similarmovies.get(position);
                Intent intent = new Intent(MovieProfileActivity.this, MovieProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", movie.getId());
                bundle.putString("name", movie.getTitle());
                bundle.putString("poster_path", movie.getPoster_path());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onFavoriteSelected(int position) {
                Movies movie= similarmovies.get(position);
                int x=movie.getIsFavourite();
                Favourite favourite = new Favourite(movie.getId(),movie.getTitle(),movie.getPoster_path(),1);
                if(x==0){
                    Toast.makeText(MovieProfileActivity.this,"added to favorites",Toast.LENGTH_SHORT).show();
                    movie.setIsFavourite(1);
                    favoritesDAO.insertFavourite(favourite);
                }
                if(x==1){
                    Toast.makeText(MovieProfileActivity.this,"removed from favorites",Toast.LENGTH_SHORT).show();
                    movie.setIsFavourite(0);
                    favoritesDAO.deleteFavourite(favourite);
                }
                moviesDAO.insertMovie(movie);

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
                Intent intent1 = new Intent(MovieProfileActivity.this, VideoPlayerActivity.class);
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
        Call<MovieVideoResponse> call = ApiClient.getInstance().getMovieApi().getTrailers(id, "9e88cc754362f676e652e8856be5d62d");
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

                Toast.makeText(MovieProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void fetchSimilarMovies(int id) {

        Call<MovieResponse> call = ApiClient.getInstance().getMovieApi().getSimilarMovies(id, "9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse response1 = response.body();
                ArrayList<Movies> moviesList = response1.getMovies();
                if (moviesList != null) {
                    similarmovies.clear();
                    similarmovies.addAll(moviesList);
                    adapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MovieProfileActivity.this, "Similar movies error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCasts(int id) {
        Call<MovieCastResponse> call = ApiClient.getInstance().getMovieApi().getCastDetails(id, "9e88cc754362f676e652e8856be5d62d");
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
                Log.d("abab", casts.get(0).getName());

            }

            @Override
            public void onFailure(Call<MovieCastResponse> call, Throwable t) {
                Log.d("abab", "failure");
                Toast.makeText(MovieProfileActivity.this, "cast error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDetails(int id) {
        Call<MovieDetails> call = ApiClient.getInstance().getMovieApi().getMovieDetails(id, "9e88cc754362f676e652e8856be5d62d");
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                MovieDetails movieDetails = response.body();
                textView.setText(movieDetails.getTitle());
                star.setText(movieDetails.getVote_average() + "");
                overview.setText("OVERVIEW" + "\n" + movieDetails.getOverview());
                String genre = "";
                for (int i = 0; i < movieDetails.getGenres().size(); i++) {
                    genre = genre + "  " + movieDetails.getGenres().get(i).name;
                }
                genres.setText(genre);
                Picasso.get().load("http://image.tmdb.org/t/p/original/" + movieDetails.getPoster_path()).into(imageView1);
                //Glide.with(MovieProfileActivity.this).load("http://image.tmdb.org/t/p/original/"+movieDetails.getPoster_path()).into(imageView1);
                Picasso.get().load("http://image.tmdb.org/t/p/w780/" + movieDetails.getBackdrop_path()).resize(width, 0).into(imageView);
                // Glide.with(MovieProfileActivity.this).load("http://image.tmdb.org/t/p/w780/"+movieDetails.getBackdrop_path()).override(width,width/2).into(imageView);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Toast.makeText(MovieProfileActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
