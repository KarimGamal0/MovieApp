package com.example.movieapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.R;
import com.example.movieapp.adapters.ReviewsAdapter;
import com.example.movieapp.adapters.TrailersAdapter;
import com.example.movieapp.data.Movie;
import com.example.movieapp.data.Review;
import com.example.movieapp.data.Trailer;
import com.example.movieapp.fragments.DetailFragment;

public class DetailActivity extends AppCompatActivity
        implements TrailersAdapter.TrailerAdapterClickHandler,
        ReviewsAdapter.ReviewAdapterClickHanldler{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DetailFragment detailFragment = (DetailFragment)
                getFragmentManager().findFragmentById(R.id.detail_frag);

        Movie movie = (Movie) getIntent().getParcelableExtra("MovieData");
        detailFragment.setMovie(movie);
    }


    @Override
    public void onClick(Trailer trailer) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
        startActivity(Intent.createChooser(intent, "Open with"));
    }

    @Override
    public void onClick(Review review) {
        Intent intent = new Intent (Intent.ACTION_VIEW,Uri.parse(review.getUrl()));
        startActivity(Intent.createChooser(intent, "Open with"));
    }
}
