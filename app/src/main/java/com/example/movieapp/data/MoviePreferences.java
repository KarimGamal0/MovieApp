package com.example.movieapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.movieapp.R;

public final class MoviePreferences {

    public static void setMovieList(Context context, String list) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        String listKey = context.getString(R.string.list_key);
        editor.putString(listKey, list);
        editor.apply();
    }


}
