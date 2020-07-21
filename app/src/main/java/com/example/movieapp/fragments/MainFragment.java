package com.example.movieapp.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.MovieLoader;
import com.example.movieapp.R;
import com.example.movieapp.adapters.MovieAdapter;
import com.example.movieapp.data.Movie;
import com.example.movieapp.data.MoviePreferences;
import com.example.movieapp.utilities.NetworkUtils;

import java.util.List;

public class MainFragment extends Fragment
        implements LoaderCallbacks<List<Movie>>
        ,SharedPreferences.OnSharedPreferenceChangeListener/*,
        MovieAdapter.MovieAdapterOnClickHandler*/ {

    private String LOG_TAG = MainFragment.class.getName();
    private SharedPreferences sharedpreferences;

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageTextView;

    private static final int MOVIE_LOADER_ID = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true);
        initViews(view);

        LinearLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 2);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(getActivity(), (MovieAdapter.MovieAdapterOnClickHandler) getActivity());

        mRecyclerView.setAdapter(mMovieAdapter);

        int loaderId = MOVIE_LOADER_ID;

        LoaderManager loaderManager = getLoaderManager();

        LoaderCallbacks<List<Movie>> callback = MainFragment.this;

        Bundle bundleForLoader = null;

        loaderManager.initLoader(loaderId, bundleForLoader, callback);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void initViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_movies);
        mLoadingIndicator = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);
        mErrorMessageTextView = (TextView) view.findViewById(R.id.tv_error_message);
    }


    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        mLoadingIndicator.setVisibility(View.VISIBLE);

        Uri baseUri = Uri.parse(NetworkUtils.MOVIE_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        uriBuilder.appendPath(sharedpreferences.getString(getString(R.string.list_key), getString(R.string.popular)));

        uriBuilder.appendQueryParameter("api_key", NetworkUtils.API_KEY);

        Log.e(LOG_TAG, uriBuilder.toString());

        return new MovieLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        Log.i(LOG_TAG, "TEST : onLoadFinished() called ...");

        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMovieAdapter.setMovieData(data);
        if (data == null) {
            showErrorMessage();
        } else {
            showMovieDataView();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
    }

    private void showMovieDataView() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movies_lists, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular_movies) {
            MoviePreferences.setMovieList(getActivity(), getString(R.string.popular));
            return true;
        }
        if (id == R.id.top_rated) {
            MoviePreferences.setMovieList(getActivity(), getString(R.string.top_rated));
            return true;
        }
        if (id == R.id.favorites) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.list_key))) {

            int loaderId = MOVIE_LOADER_ID;
            LoaderManager loaderManager = getLoaderManager();
            LoaderCallbacks<List<Movie>> callback = MainFragment.this;
            Bundle bundleForLoader = null;
            loaderManager.restartLoader(loaderId, bundleForLoader, callback);
        }
    }
}