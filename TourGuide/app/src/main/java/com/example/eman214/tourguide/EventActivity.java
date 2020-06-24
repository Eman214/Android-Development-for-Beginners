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

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_list);

        //Construct the data source
        final ArrayList<Attraction> attractions = new ArrayList<>();

        Location frightNight = new Location(getResources().getString(R.string.restaurant_name_1));
        frightNight.setLatitude(38.897650);
        frightNight.setLongitude(-77.026271);
        attractions.add(new Attraction(getResources().getString(R.string.event_name_1),
                getResources().getString(R.string.event_date_1),
                getString(R.string.event_address_1),
                R.drawable.event_img_1, frightNight));

        Location comedyFestival = new Location(getResources().getString(R.string.restaurant_name_1));
        comedyFestival.setLatitude(38.931939);
        comedyFestival.setLongitude(-76.997072);
        attractions.add(new Attraction(getResources().getString(R.string.event_name_2),
                getResources().getString(R.string.event_date_2),
                getResources().getString(R.string.event_address_2),
                R.drawable.event_img_2, comedyFestival));

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
                String address = attractions.get(position).getAddress();

                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmIntentUri = Uri.parse("geo:" + lat + "," + lon + "?q=" + Uri.encode(address));

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

