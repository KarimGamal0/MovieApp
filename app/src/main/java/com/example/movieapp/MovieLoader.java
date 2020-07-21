package com.example.movieapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.movieapp.data.Movie;
import com.example.movieapp.utilities.NetworkUtils;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private static final String LOG_TAG = MovieLoader.class.getSimpleName();

    private String mUrl;

    private List<Movie> mMovieData = null;
    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST : onStartLoading() called ...");
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        Log.i(LOG_TAG, "TEST : loadInBackground() called ...");
        if (mUrl == null) {
            return null;
        }
        List<Movie> movies = NetworkUtils.fetchMovieData(mUrl);
        return movies;
    }

    @Override
    public void deliverResult(List<Movie> data) {
        super.deliverResult(data);
        mMovieData = data;
    }
}
