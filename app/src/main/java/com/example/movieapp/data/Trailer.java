package com.example.movieapp.data;

public class Trailer {

    private String mId;
    private String mIso_639_1;
    private String mIso_3166_1;
    private String mKey;
    private String mName;
    private String mSite;
    private int mSize;
    private String mType;

    public Trailer(String id, String iso_639_1, String iso_3166_1, String key,
                   String name, String site, int size, String type) {
        mId = id;
        mIso_639_1 = iso_639_1;
        mIso_3166_1 = iso_3166_1;
        mKey = key;
        mName = name;
        mSite = site;
        mSize = size;
        mType = type;
    }


    public String getId() {
        return mId;
    }

    public String getIso_639_1() {
        return mIso_639_1;
    }

    public String getIso_3166_1() {
        return mIso_3166_1;
    }


    public String getKey() {
        return mKey;
    }

    public String getName() {
        return mName;
    }

    public String getSite() {
        return mSite;
    }

    public int getSize() {
        return mSize;
    }

    public String getType() {
        return mType;
    }
}
