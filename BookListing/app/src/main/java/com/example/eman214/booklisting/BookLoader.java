package com.example.eman214.booklisting;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private static final String LOG_TAG = BookLoader.class.getName();
    private String mUrl;

    public BookLoader(@NonNull Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG, "Test: onStartLoading() called...");
        forceLoad();
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {
        Log.e(LOG_TAG, "Test: loadInBackground() called...");
        if (mUrl == null) {
            return null;
        }
        return QueryUtils.fetchBookInfo(mUrl);
    }
}
