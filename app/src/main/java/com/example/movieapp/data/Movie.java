package com.example.movieapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private double mPopularity;
    private int mVote_count;
    private boolean mVideo;
    private String mPoster_path;
    private int mId;
    private boolean mAdult;
    private String mBackdrop_path;
    private String mOriginal_Language;
    private String mOriginal_title;
    private int[] mGenre_ids;
    private String mTitle;
    private int mVote_average;
    private String mOverview;
    private String mRelease_date;

    public Movie(double popularity, int vote_count, boolean video, String poster_path,
                 int id, boolean adult, String backdrop_path, String original_language,
                 String original_title, int[] genre_ids, String title, int vote_average,
                 String overview, String release_date) {
        mPopularity = popularity;
        mVote_count = vote_count;
        mVideo = video;
        mPoster_path = poster_path;
        mId = id;
        mAdult = adult;
        mBackdrop_path = backdrop_path;
        mOriginal_Language = original_language;
        mOriginal_title = original_title;
        mGenre_ids = genre_ids;
        mTitle = title;
        mVote_average = vote_average;
        mOverview = overview;
        mRelease_date = release_date;
    }

    public double getPopularity() {
        return mPopularity;
    }


    public int getVote_count() {
        return mVote_count;
    }

    public boolean isVideo() {
        return mVideo;
    }

    public String getPoster_path() {
        return mPoster_path;
    }

    public int getId() {
        return mId;
    }

    public boolean isAdult() {
        return mAdult;
    }

    public String getBackdrop_path() {
        return mBackdrop_path;
    }

    public String getOriginal_Language() {
        return mOriginal_Language;
    }

    public String getOriginal_title() {
        return mOriginal_title;
    }

    public int[] getGenre_ids() {
        return mGenre_ids;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getVote_average() {
        return mVote_average;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getRelease_date() {
        return mRelease_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.mPopularity);
        dest.writeInt(this.mVote_count);
        //dest.writeBoolean(this.mVideo);
        dest.writeString(this.mPoster_path);
        dest.writeInt(this.mId);
        //dest.writeBoolean(this.mAdult);
        dest.writeString(this.mBackdrop_path);
        dest.writeString(this.mOriginal_Language);
        dest.writeString(this.mOriginal_title);
        dest.writeIntArray(this.mGenre_ids);
        dest.writeString(this.mTitle);
        dest.writeInt(this.mVote_average);
        dest.writeString(this.mOverview);
        dest.writeString(this.mRelease_date);
    }

    protected Movie(Parcel in) {
        this.mPopularity = in.readDouble();
        this.mVote_count = in.readInt();
        //this.mVideo = in.readBoolean();
        this.mPoster_path = in.readString();
        this.mId = in.readInt();
        //this.mAdult = in.readBoolean();
        this.mBackdrop_path = in.readString();
        this.mOriginal_Language = in.readString();
        this.mOriginal_title = in.readString();
        this.mGenre_ids = in.createIntArray();
        this.mTitle = in.readString();
        this.mVote_average = in.readInt();
        this.mOverview = in.readString();
        this.mRelease_date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
