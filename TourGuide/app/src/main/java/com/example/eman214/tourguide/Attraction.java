package com.example.eman214.tourguide;

import android.location.Location;

public class Attraction {

    //String resource for the attraction name
    private String mName;

    //String resource for the attraction contact number
    private String mContact = null;

    //String resource for the attraction address
    private String mAddress;

    //Image resource ID for the attraction
    private int mImgResourceId;

    private Location mLocation;

    /**
     * Create a new Attraction object
     *
     * @param name          of the attraction
     * @param contact       number of the attraction
     * @param address       of the attraction
     * @param imgResourceId is the image associated with the attraction
     */
    public Attraction(String name, String contact, String address, int imgResourceId, Location location) {
        mName = name;
        mContact = contact;
        mAddress = address;
        mImgResourceId = imgResourceId;
        mLocation = location;
    }

    /**
     * Create a new Attraction object without contact parameter
     *
     * @param name          of the attraction
     * @param address       of the attraction
     * @param imgResourceId is the image associated with the attraction
     */
    public Attraction(String name, String address, int imgResourceId, Location location) {
        mName = name;
        mAddress = address;
        mImgResourceId = imgResourceId;
        mLocation = location;
    }

    /**
     * Gets the string value for the defaultTranslation.
     *
     * @return current name.
     */
    public String getName() {
        return mName;
    }

    public String getContact() {
        return mContact;
    }

    public String getAddress() {
        return mAddress;
    }

    public boolean hasContact() {
        return mContact != null;
    }

    public int getImgResourceId() {
        return mImgResourceId;
    }

    public Location getLocation() {
        return mLocation;
    }
}
