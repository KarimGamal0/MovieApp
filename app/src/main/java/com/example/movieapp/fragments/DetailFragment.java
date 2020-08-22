package com.example.movieapp.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.adapters.ReviewsAdapter;
import com.example.movieapp.adapters.TrailersAdapter;
import com.example.movieapp.data.Movie;
import com.example.movieapp.data.Review;
import com.example.movieapp.data.Trailer;
import com.example.movieapp.databinding.FragmentDetailBinding;
import com.example.movieapp.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.List;

public class DetailFragment extends Fragment {

    private final String LOG_TAG = DetailFragment.class.getSimpleName();

    private Movie movie;

    private MultiSnapRecyclerView mRecyclerViewTrailers;
    private TrailersAdapter mTrailerAdapter;

    private MultiSnapRecyclerView mRecyclerViewReviews;
    private ReviewsAdapter mReviewsAdapter;

    private FragmentDetailBinding mDetailBinding;

    private static final int TRAILER_LOADER_ID = 2;
    private static final int REVIEWS_LOADER_ID = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mDetailBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_detail);

        movie = (Movie) getActivity().getIntent().getParcelableExtra("MovieData");

        Picasso.get().load(NetworkUtils.IMAGE_URL + NetworkUtils.img_sizes[4] + movie.getBackdrop_path())
                .into(mDetailBinding.moviePoster);

        mDetailBinding.mainData.overview.setText(movie.getOverview());
        mDetailBinding.mainData.voteAverage.setText(String.valueOf(movie.getVote_average()));
        mDetailBinding.mainData.voteCount.setText(String.valueOf(movie.getVote_count()));
        mDetailBinding.mainData.popularity.setText(String.valueOf(movie.getPopularity()));
        mDetailBinding.mainData.originalLanguage.setText(movie.getOriginal_Language());
        mDetailBinding.mainData.releaseDate.setText(movie.getRelease_date());
        /*if (movie.isAdult()) {
            mDetailBinding.mainData.adult.setText("R");
        }*/

        // adapter and recycler of trailers
        mRecyclerViewTrailers = mDetailBinding.trailersData;
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        mRecyclerViewTrailers.setLayoutManager(layoutManager1);
        mRecyclerViewTrailers.setHasFixedSize(true);

        mTrailerAdapter = new TrailersAdapter(getActivity(), (TrailersAdapter.TrailerAdapterClickHandler) getActivity());
        mRecyclerViewTrailers.setAdapter(mTrailerAdapter);

        // adapter and recycler of reviews
        mRecyclerViewReviews = mDetailBinding.reviewsData;
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        mRecyclerViewReviews.setLayoutManager(layoutManager2);
        mRecyclerViewReviews.setHasFixedSize(true);

        mReviewsAdapter = new ReviewsAdapter(getActivity(), (ReviewsAdapter.ReviewAdapterClickHanldler) getActivity());
        mRecyclerViewReviews.setAdapter(mReviewsAdapter);


        int trailerLoaderId = TRAILER_LOADER_ID;
        int reviewsLoaderId = REVIEWS_LOADER_ID;
        LoaderManager loaderManager = getLoaderManager();
        Bundle bundleForLoader = null;
        loaderManager.initLoader(trailerLoaderId, bundleForLoader, trailers_loader);
        loaderManager.initLoader(reviewsLoaderId, bundleForLoader, reviews_loader);
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    private LoaderCallbacks<List<Trailer>> trailers_loader = new LoaderCallbacks<List<Trailer>>() {
        @Override
        public Loader<List<Trailer>> onCreateLoader(int id, Bundle args) {

            Uri baseUri = Uri.parse(NetworkUtils.MOVIE_REQUEST_URL);
            final Uri.Builder uriBuilder = baseUri.buildUpon();

            uriBuilder.appendPath(String.valueOf(movie.getId()));
            uriBuilder.appendPath(getString(R.string.videos));
            uriBuilder.appendQueryParameter("api_key", NetworkUtils.API_KEY);

            Log.e(LOG_TAG, uriBuilder.toString());

            return new AsyncTaskLoader<List<Trailer>>(getActivity()) {

                private List<Trailer> mTrailerData = null;

                @Override
                protected void onStartLoading() {
                    Log.i(LOG_TAG, "TEST : onStartLoading() called ...");
                    forceLoad();
                }

                @Override
                public List<Trailer> loadInBackground() {
                    Log.i(LOG_TAG, "TEST : loadInBackground() called ...");
                    if (uriBuilder.toString() == null) {
                        return null;
                    }
                    List<Trailer> trailers = NetworkUtils.fetchTrailersData(uriBuilder.toString());
                    return trailers;
                }

                @Override
                public void deliverResult(List<Trailer> data) {
                    super.deliverResult(data);
                    mTrailerData = data;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> data) {
            Log.i(LOG_TAG, "TEST : onLoadFinished() called ...");

            mTrailerAdapter.setTrailersData(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Trailer>> loader) {

        }
    };

    private LoaderCallbacks<List<Review>> reviews_loader = new LoaderCallbacks<List<Review>>() {
        @Override
        public Loader<List<Review>> onCreateLoader(int id, Bundle args) {

            Uri baseUri = Uri.parse(NetworkUtils.MOVIE_REQUEST_URL);
            final Uri.Builder uriBuilder = baseUri.buildUpon();

            uriBuilder.appendPath(String.valueOf(movie.getId()));
            uriBuilder.appendPath(getString(R.string.reviews));
            uriBuilder.appendQueryParameter("api_key", NetworkUtils.API_KEY);

            Log.e(LOG_TAG, uriBuilder.toString());

            return new AsyncTaskLoader<List<Review>>(getActivity()) {
                private List<Review> mReviewData = null;

                @Override
                protected void onStartLoading() {
                    Log.i(LOG_TAG, "TEST : onStartLoading() called ...");
                    forceLoad();
                }

                @Override
                public List<Review> loadInBackground() {
                    Log.i(LOG_TAG, "TEST : loadInBackground() called ...");
                    if (uriBuilder.toString() == null) {
                        return null;
                    }
                    List<Review> reviews = NetworkUtils.fetchReviewsData(uriBuilder.toString());
                    return reviews;
                }

                @Override
                public void deliverResult(List<Review> data) {
                    super.deliverResult(data);
                    mReviewData = data;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {
            Log.i(LOG_TAG, "TEST : onLoadFinished() called ...");

            mReviewsAdapter.setReviewsData(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Review>> loader) {

        }
    };
}