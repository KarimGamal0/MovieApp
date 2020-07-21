package com.example.movieapp.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.movieapp.data.Movie;
import com.example.movieapp.data.Review;
import com.example.movieapp.data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OpenMovieJsonUtils {

    // Movies request URL data
    public static final String OMM_RESULTS = "results";
    public static final String OMM_POPULARITY = "popularity";
    public static final String OMM_VOTE_COUNT = "vote_count";
    public static final String OMM_VIDEO = "video";
    public static final String OMM_POSTER_PATH = "poster_path";
    public static final String OMM_ID = "id";
    public static final String OMM_ADULT = "adult";
    public static final String OMM_BACKDROP_PATH = "backdrop_path";
    public static final String OMM_ORIGINAL_LANGUAGE = "original_language";
    public static final String OMM_ORIGINAL_TITLE = "original_title";
    public static final String OMM_GENRE_IDS = "genre_ids";
    public static final String OMM_TITLE = "title";
    public static final String OMM_VOTE_AVERAGE = "vote_average";
    public static final String OMM_OVERVIEW = "overview";
    public static final String OMM_RELEASE_DATE = "release_date";

    //Trailer Request URL data
    public static final String OTM_ISO_639_1 = "iso_639_1";
    public static final String OTM_ISO_3166_1 = "iso_3166_1";
    public static final String OTM_KEY = "key";
    public static final String OTM_NAME = "name";
    public static final String OTM_SITE = "site";
    public static final String OTM_SIZE = "size";
    public static final String OTM_TYPE = "type";

    //Review Request URL data
    public static final String ORM_AUTHOR = "author";
    public static final String ORM_CONTENT = "content";
    public static final String ORM_URL = "url";

    public static List<Movie> extractFeatureFromJson(String movieJSON) {
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(movieJSON);

            JSONArray movieArray = baseJsonResponse.getJSONArray(OMM_RESULTS);

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject currentMovie = movieArray.getJSONObject(i);
                double popularity = currentMovie.getDouble(OMM_POPULARITY);

                int vote_count = currentMovie.getInt(OMM_VOTE_COUNT);

                boolean video = currentMovie.getBoolean(OMM_VIDEO);

                String poster_path = currentMovie.getString(OMM_POSTER_PATH);

                int id = currentMovie.getInt(OMM_ID);

                boolean adult = currentMovie.getBoolean(OMM_ADULT);

                String backdrop_path = currentMovie.getString(OMM_BACKDROP_PATH);

                String original_language = currentMovie.getString(OMM_ORIGINAL_LANGUAGE);

                String original_title = currentMovie.getString(OMM_ORIGINAL_TITLE);

                JSONArray genresArray = currentMovie.getJSONArray(OMM_GENRE_IDS);

                int[] genre_ids = new int[genresArray.length()];

                for (int j = 0; j < genre_ids.length; j++) {
                    genre_ids[j] = (int) genresArray.get(j);
                }

                String title = currentMovie.getString(OMM_TITLE);

                int vote_average = currentMovie.getInt(OMM_VOTE_AVERAGE);

                String overview = currentMovie.getString(OMM_OVERVIEW);

                String release_date = currentMovie.getString(OMM_RELEASE_DATE);

                Movie movie = new Movie(popularity, vote_count, video, poster_path, id, adult, backdrop_path,
                        original_language, original_title, genre_ids, title, vote_average, overview, release_date);

                movies.add(movie);
            }

        } catch (JSONException e) {
            Log.e("NetworkUtils", "Problem parsing the movie JSON results", e);
        }
        return movies;
    }

    public static List<Trailer> extractTrailersFromJson(String trailersJSON) {
        if (TextUtils.isEmpty(trailersJSON)) {
            return null;
        }

        List<Trailer> trailers = new ArrayList<>();

        try {
            JSONObject baseJsonObject = new JSONObject(trailersJSON);

            JSONArray trailersArray = baseJsonObject.getJSONArray(OMM_RESULTS);

            for (int i = 0; i < trailersArray.length(); i++) {
                JSONObject currentTrailer = trailersArray.getJSONObject(i);

                String id = currentTrailer.getString(OMM_ID);

                String iso_639_1 = currentTrailer.getString(OTM_ISO_639_1);

                String iso_3166_1 = currentTrailer.getString(OTM_ISO_3166_1);

                String key = currentTrailer.getString(OTM_KEY);

                String name = currentTrailer.getString(OTM_NAME);

                String site = currentTrailer.getString(OTM_SITE);

                int size = currentTrailer.getInt(OTM_SIZE);

                String type = currentTrailer.getString(OTM_TYPE);

                Trailer trailer = new Trailer(id, iso_639_1, iso_3166_1, key, name, site, size, type);

                trailers.add(trailer);
            }
        } catch (JSONException e) {
            Log.e("NetworkUtils", "Problem parsing the trailer JSON results", e);
        }
        return trailers;
    }

    public static List<Review> extractReviewFromJson(String reviewJSON) {
        if (TextUtils.isEmpty(reviewJSON)) {
            return null;
        }
        List<Review> reviews = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(reviewJSON);

            JSONArray reviewArray = baseJsonResponse.getJSONArray(OMM_RESULTS);

            for (int i = 0; i < reviewArray.length(); i++) {
                JSONObject currentReview = reviewArray.getJSONObject(i);

                String author = currentReview.getString(ORM_AUTHOR);

                String content = currentReview.getString(ORM_CONTENT);

                String id = currentReview.getString(OMM_ID);

                String url = currentReview.getString(ORM_URL);

                Review review = new Review(author, content, id, url);

                reviews.add(review);
            }
        } catch (JSONException e) {
            Log.e("NetworkUtils", "Problem parsing the Review JSON results", e);
        }

        return reviews;
    }
}