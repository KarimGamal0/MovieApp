package com.example.movieapp.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.R;
import com.example.movieapp.adapters.MovieAdapter;
import com.example.movieapp.data.Movie;
import com.example.movieapp.fragments.DetailFragment;
import com.example.movieapp.fragments.MainFragment;

public class MainActivity extends AppCompatActivity implements
         MovieAdapter.MovieAdapterOnClickHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        View fragmentContainer = findViewById(R.id.movie_list_frag);
        if (fragmentContainer != null) {
            MainFragment list = new MainFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.movie_list_frag, list);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    @Override
    public void onClick(Movie movie) {
        View fragmentContainer = findViewById(R.id.frag_container);
        if (fragmentContainer != null) {
            DetailFragment details = new DetailFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            details.setMovie(movie);
            ft.replace(R.id.frag_container, details);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            Intent movieDetailIntent = new Intent(MainActivity.this, DetailActivity.class);
            movieDetailIntent.putExtra("MovieData", movie);
            startActivity(movieDetailIntent);
        }
    }
}