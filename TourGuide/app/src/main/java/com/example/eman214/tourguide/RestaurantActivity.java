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

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_list);

        //Construct the data source
        final ArrayList<Attraction> attractions = new ArrayList<>();

        Location ambar = new Location(getResources().getString(R.string.restaurant_name_1));
        ambar.setLatitude(38.888980);
        ambar.setLongitude(-76.995150);
        attractions.add(new Attraction(getResources().getString(R.string.restaurant_name_1),
                getResources().getString(R.string.restaurant_contact_1),
                getResources().getString(R.string.restaurant_address_1),
                R.drawable.restaurant_img_1, ambar));

        Location pembroke = new Location(getResources().getString(R.string.restaurant_name_1));
        pembroke.setLatitude(38.910417);
        pembroke.setLongitude(-77.042994);
        attractions.add(new Attraction(getResources().getString(R.string.restaurant_name_2),
                getResources().getString(R.string.restaurant_contact_2),
                getResources().getString(R.string.restaurant_address_2),
                R.drawable.restaurant_img_2, ambar));

        Location busboysAndPoets = new Location(getResources().getString(R.string.restaurant_name_1));
        busboysAndPoets.setLatitude(38.902197);
        busboysAndPoets.setLongitude(-77.018332);
        attractions.add(new Attraction(getResources().getString(R.string.restaurant_name_3),
                getResources().getString(R.string.restaurant_contact_3),
                getResources().getString(R.string.restaurant_address_3),
                R.drawable.restaurant_img_3, busboysAndPoets));

        Location bensChiliBowl = new Location(getResources().getString(R.string.restaurant_name_1));
        bensChiliBowl.setLatitude(38.917252);
        bensChiliBowl.setLongitude(-77.028799);
        attractions.add(new Attraction(getResources().getString(R.string.restaurant_name_4),
                getResources().getString(R.string.restaurant_contact_4),
                getResources().getString(R.string.restaurant_address_4),
                R.drawable.restaurant_img_4, bensChiliBowl));

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
