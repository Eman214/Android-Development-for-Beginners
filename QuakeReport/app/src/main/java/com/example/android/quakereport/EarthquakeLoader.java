package com.example.android.quakereport;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;


public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    private String[] mUrl;

    public EarthquakeLoader(@NonNull Context context, String... url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG, "Test: onStartLoading() called...");
        forceLoad();
    }


    @Override
    public List<Earthquake> loadInBackground() {
        Log.e(LOG_TAG, "Test: loadInBackground() called...");
        if (mUrl.length < 1 || mUrl[0] == null) {
            return null;
        }

        return QueryUtils.fetchEarthquakeData(mUrl[0]);
    }
}
