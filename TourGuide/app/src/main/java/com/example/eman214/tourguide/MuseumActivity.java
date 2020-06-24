package com.example.eman214.tourguide;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MuseumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_list);

        //Construct the data source
        final ArrayList<Attraction> attractions = new ArrayList<>();

        //Declare the location and sets the latitude and longitude for the location
        Location madameTussauds = new Location(getResources().getString(R.string.restaurant_name_1));
        madameTussauds.setLatitude(38.897650);
        madameTussauds.setLongitude(-77.026271);
        attractions.add(new Attraction(getResources().getString(R.string.museum_name_1),
                getResources().getString(R.string.museum_contact_1),
                getResources().getString(R.string.museum_address_1),
                R.drawable.museum_img_1, madameTussauds));

        Location africanAmericanHistory = new Location(getResources().getString(R.string.restaurant_name_1));
        africanAmericanHistory.setLatitude(38.891062);
        africanAmericanHistory.setLongitude(-77.032615);
        attractions.add(new Attraction(getResources().getString(R.string.museum_name_2),
                getResources().getString(R.string.museum_contact_2),
                getResources().getString(R.string.museum_address_2),
                R.drawable.museum_img_2, africanAmericanHistory));

        Location airAndSpace = new Location(getResources().getString(R.string.restaurant_name_1));
        airAndSpace.setLatitude(38.889040);
        airAndSpace.setLongitude(-77.026320);
        attractions.add(new Attraction(getResources().getString(R.string.museum_name_3),
                getResources().getString(R.string.museum_contact_3),
                getResources().getString(R.string.museum_address_3),
                R.drawable.museum_img_3, airAndSpace));

        Location basilica = new Location(getResources().getString(R.string.restaurant_name_1));
        basilica.setLatitude(38.933381);
        basilica.setLongitude(-77.000639);
        attractions.add(new Attraction(getResources().getString(R.string.museum_name_4),
                getResources().getString(R.string.museum_contact_4),
                getResources().getString(R.string.museum_address_4),
                R.drawable.museum_img_4, basilica));

        //Create an adapter to convert the array to views
        AttractionAdapter adapter = new AttractionAdapter(this, attractions);


        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get longitutde, latitude, and name of the clicked item based on the position
                double lat = attractions.get(position).getLocation().getLatitude();
                double lon = attractions.get(position).getLocation().getLongitude();
                String name = attractions.get(position).getName();

                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmIntentUri = Uri.parse("geo:" + lat + "," + lon + "?q=" + Uri.encode(name));

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start an activity that can handle the Intent
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

    }
}

