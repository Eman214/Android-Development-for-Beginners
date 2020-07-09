package com.example.android.quakereport;

public class Earthquake {

    //Magnitude of the earthquake
    private double mMag;

    //Location of the earthquake
    private String mLocation;

    //Date of the earthquake in unix time
    private long mTimeInMilliseconds;

    private String mUrl;


    /**
     * Create a new {@link Earthquake} object
     *
     * @param mag is the magnitude or size of the earthquake
     * @param location is the location of the earthquake
     * @param timeInMilliseconds is the time that the earthquake occurred in unix time
     */
    public Earthquake(double mag, String location, Long timeInMilliseconds, String url) {
        mMag = mag;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    //get functions
    public double getMag() {
        return mMag;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getUrl() { return mUrl;}
}
