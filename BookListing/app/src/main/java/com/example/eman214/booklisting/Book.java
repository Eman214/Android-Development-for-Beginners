package com.example.eman214.booklisting;

import android.net.Uri;

import java.net.URI;

public class Book {

    public static final String LOG_TAG = Book.class.getName();
    private String mTitle;
    private String[] mAuthors;
    private String mDescription;
    private String mUrl;
    private String mRating;

    public Book(String title, String[] authors, String description, String url, String rating)  {
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mUrl = url;
        mRating = rating;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getRating() {
        return mRating;
    }

    public String getAuthors() {
        StringBuilder s = new StringBuilder();
        for(int i=0;i<mAuthors.length;i++) {
            if(i == mAuthors.length-1)
                s.append(mAuthors[i]);
            else
                s.append(mAuthors[i]).append(", ");
        }
        return s.toString();
    }

}
