package com.example.movieapp.utilities;

import android.util.Log;

import com.example.movieapp.BuildConfig;
import com.example.movieapp.data.Movie;
import com.example.movieapp.data.Review;
import com.example.movieapp.data.Trailer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class NetworkUtils {

    public static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public static final String MOVIE_REQUEST_URL = "https://api.themoviedb.org/3/movie";
    public static final String API_KEY = BuildConfig.API_KEY;

    public static String IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static String[] img_sizes = {"w92", "w154", "w185", "w342", "w500", "w780"};


    private static URL createUrl(String StringUrl) {
        URL url = null;
        try {
            url = new URL(StringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);

        }
        return url;
    }

    public static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Movie> fetchMovieData(String requestUrl) {

        Log.i(LOG_TAG, "TEST : fetchMovieData() called...");

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        List<Movie> movies = OpenMovieJsonUtils.extractFeatureFromJson(jsonResponse);
        return movies;
    }

    public static List<Trailer> fetchTrailersData(String requestUrl) {
        Log.i(LOG_TAG, "TEST : fetchTrailersData() called...");

        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Trailer> trailers = OpenMovieJsonUtils.extractTrailersFromJson(jsonResponse);
        return trailers;
    }

    public static List<Review> fetchReviewsData(String requestUrl) {
        Log.i(LOG_TAG, "TEST : fetchReviewsData() called...");

        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Review> reviews = OpenMovieJsonUtils.extractReviewFromJson(jsonResponse);
        return reviews;
    }
}