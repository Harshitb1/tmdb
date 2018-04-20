package com.example.sagar.moviestvshows;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesFragment.MovieSelectedCallback, TvFragment.TvShowSelectedCallback, FavoriteFragment.OnFavoriteSelected{


    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;

    private final static String pageTitle[] = {"MOVIES", "TV SHOWS","Favorites"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MoviesFragment(),pageTitle[0]);
        viewPagerAdapter.addFragment(new TvFragment(),pageTitle[1]);
        viewPagerAdapter.addFragment(new FavoriteFragment(),pageTitle[2]);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(true,new CubeOutRotationTransformation());
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.cardview_dark_background));

    }


    @Override
    public void onMovieSelected(Movies movie) {
        Intent intent = new Intent(MainActivity.this,MovieProfileActivity.class);
        Bundle bundle= new Bundle();
        bundle.putInt("id",movie.getId());
        bundle.putString("name",movie.getTitle());
        bundle.putString("poster_path",movie.getPoster_path());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onTvShowSelected(TvShows show) {

        Intent intent = new Intent(MainActivity.this,TvShowProfileActivity.class);
        Bundle bundle= new Bundle();
        bundle.putInt("id",show.getId());
        bundle.putString("name",show.getName());
        bundle.putString("poster_path",show.getPoster_path());
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onclick(Favourite favourite) {
        Intent intent= new Intent(MainActivity.this,MovieProfileActivity.class);
        Bundle bundle= new Bundle();
        bundle.putInt("id",favourite.getId());
        bundle.putString("name",favourite.getName());
        bundle.putString("poster_path",favourite.getPoster_path());
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
