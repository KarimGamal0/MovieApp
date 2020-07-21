package com.example.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.data.Movie;
import com.example.movieapp.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<Movie> mMoviesData;
    private Context mContext;

    final private MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutIdForMovieListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForMovieListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieAdapterViewHolder holder, final int position) {
        final Movie movie = mMoviesData.get(position);

        Picasso.get().load(NetworkUtils.IMAGE_URL + NetworkUtils.img_sizes[2] + movie.getPoster_path())
                .into(holder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if (mMoviesData == null) {
            return 0;
        }
        return mMoviesData.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mMovieImageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);

            mMovieImageView = itemView.findViewById(R.id.movie_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMoviesData.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    public void setMovieData(List<Movie> movieData) {
        mMoviesData = movieData;
        notifyDataSetChanged();
    }

    public Movie getMovieData(int pos) {
        Movie movie = mMoviesData.get(pos);
        return movie;
    }
}