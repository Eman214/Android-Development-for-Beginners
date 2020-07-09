package com.example.android.quakereport;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(@NonNull Context context, @NonNull ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    /**
     * Returns a list item view containing the magnitude, location, and date
     * of the earthquake at the given position in the list of earthquakes
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Check if there is an existing view that can be reused, otherwise inflate a new view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }

        Earthquake currentEarthquake = getItem(position);

        //Find the text view with the view ID mag and display the magnitude
        TextView magView = listItemView.findViewById(R.id.mag);
        String magFormat = formatMagnitude(currentEarthquake.getMag());
        magView.setText(magFormat);


        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMag());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        //Get the original string location from the EarthQuake object
        String location = currentEarthquake.getLocation();
        String locationOffset;
        String primaryLocation;

        //Checks whether the location string contains the text "of"
        if (location.contains("of")) {
            //Create 2 substrings from the location string based on the position of the "of" text
            //Ex: the first String is "10km SE of" and the second String is "Tomatlan, Mexico".
            locationOffset = location.substring(0, location.indexOf("of") + 2);
            primaryLocation = location.substring(location.indexOf("of") + 3);
        } else {
            //If there is no "of" text in the location string, set the locationOffset
            //to "Near the" and the primary location to the full location string.
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = location;
        }

        //Find the text view with the view ID location_offset and display the location
        TextView locationOffsetView = listItemView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);

        //Find the text view with the view ID primary_location and display the location
        TextView primaryLocationView = listItemView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);


        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        //Find the text view with the view ID date
        TextView dateTextView = listItemView.findViewById(R.id.date);
        //Format the date string
        String formattedDate = formatDate(dateObject);
        //Display the date of the earthquake in the text view
        dateTextView.setText(formattedDate);

        //Find the text view with the view ID date
        TextView timeTextView = listItemView.findViewById(R.id.time);
        //Format the time string
        String formattedTime = formatTime(dateObject);
        //Display the time of the earthquake in the text view
        timeTextView.setText(formattedTime);

        //Return the list item view with the correct info for the current position
        return listItemView;
    }


    /**
     * Return the formatted magnitude string with 1 decimal place (i.e. "7.1)
     */
    private String formatMagnitude(double mag) {
        DecimalFormat magFormat = new DecimalFormat("0.0");
        return magFormat.format(mag);
    }

    private int getMagnitudeColor(double mag) {
        int magColorResourceID;
        int roundedMag = (int) Math.floor(mag);

        switch (roundedMag) {
            case 0:
            case 1:
                magColorResourceID = R.color.magnitude1;
                break;
            case 2:
                magColorResourceID = R.color.magnitude2;
                break;
            case 3:
                magColorResourceID = R.color.magnitude3;
                break;
            case 4:
                magColorResourceID = R.color.magnitude4;
                break;
            case 5:
                magColorResourceID = R.color.magnitude5;
                break;
            case 6:
                magColorResourceID = R.color.magnitude6;
                break;
            case 7:
                magColorResourceID = R.color.magnitude7;
                break;
            case 8:
                magColorResourceID = R.color.magnitude8;
                break;
            case 9:
                magColorResourceID = R.color.magnitude9;
                break;
            default:
                magColorResourceID = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magColorResourceID);
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}