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

public class MonumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_list);

        //Construct the data source
        final ArrayList<Attraction> attractions = new ArrayList<>();

        Location mlkMemorial = new Location(getResources().getString(R.string.restaurant_name_1));
        mlkMemorial.setLatitude(38.886391);
        mlkMemorial.setLongitude(-77.043954);
        attractions.add(new Attraction(getResources().getString(R.string.monument_name_1),
                getResources().getString(R.string.monument_address_1),
                R.drawable.monument_img_1, mlkMemorial));

        Location nationalMall = new Location(getResources().getString(R.string.restaurant_name_1));
        nationalMall.setLatitude(38.889392853);
        nationalMall.setLongitude(-77.022872925);
        attractions.add(new Attraction(getResources().getString(R.string.monument_name_2),
                getResources().getString(R.string.monument_address_2),
                R.drawable.monument_img_2, nationalMall));

        Location pentagonMemorial = new Location(getResources().getString(R.string.restaurant_name_1));
        pentagonMemorial.setLatitude(38.870692);
        pentagonMemorial.setLongitude(-77.059288);
        attractions.add(new Attraction(getResources().getString(R.string.monument_name_3),
                getResources().getString(R.string.monument_address_3),
                R.drawable.monument_img_3, pentagonMemorial));

        Location africanAmericanCivilWarMemorial = new Location(getResources().getString(R.string.restaurant_name_1));
        africanAmericanCivilWarMemorial.setLatitude(38.916218);
        africanAmericanCivilWarMemorial.setLongitude(-77.025144);
        attractions.add(new Attraction(getResources().getString(R.string.monument_name_4),
                getResources().getString(R.string.monument_address_4),
                R.drawable.monument_img_4, africanAmericanCivilWarMemorial));

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
