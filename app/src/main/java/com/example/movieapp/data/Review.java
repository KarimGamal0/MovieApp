package com.example.movieapp.data;

public class Review {

    private String mAuthor;
    private String mContent;
    private String mId;
    private String mUrl;

    public Review(String author, String content, String id, String url) {
        mAuthor = author;
        mContent = content;
        mId = id;
        mUrl = url;
    }


    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public String getId() {
        return mId;
    }

    public String getUrl() {
        return mUrl;
    }
}
