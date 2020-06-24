package com.example.eman214.tourguide;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AttractionAdapter extends ArrayAdapter<Attraction> {


    /**
     *
     * @param context The current context used to inflate the view
     * @param attractions a list of Attraction objects to display
     */
    public AttractionAdapter(Activity context, ArrayList<Attraction> attractions) {
        super(context, 0, attractions);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        // Check if an existing view is being reused, otherwise inflate the view
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //Get the Attraction item for the current position
        Attraction currentAttraction = getItem(position);

        //Find the text view with the given ID and set the attraction name
        TextView nameTextView = listItemView.findViewById(R.id.attraction_name);
        nameTextView.setText(currentAttraction.getName());
        nameTextView.setPaintFlags(nameTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

//        //Find the text view with the given ID and set the attraction date
//        TextView dateTextView = listItemView.findViewById(R.id.attraction_date);
//        if (currentAttraction.hasDate()) {
//            dateTextView.setText(currentAttraction.getDate());
//            dateTextView.setVisibility(View.VISIBLE);
//        } else {
//            dateTextView.setVisibility(View.GONE);
//        }
        //Find the text view with the given ID and set the attraction contact number
        TextView contactTextView = listItemView.findViewById(R.id.attraction_contact);
        if (currentAttraction.hasContact()) {
            contactTextView.setText(currentAttraction.getContact());
            contactTextView.setVisibility(View.VISIBLE);
        } else {
            contactTextView.setVisibility(View.GONE);
        }


        //Find the text view with the given ID and set the attraction address
        TextView addressTextView = listItemView.findViewById(R.id.attraction_address);
        addressTextView.setText(currentAttraction.getAddress());

        //Find the text view with the given ID and set the attraction image
        ImageView imageView = listItemView.findViewById(R.id.image);
        imageView.setImageResource(currentAttraction.getImgResourceId());

        return listItemView;
    }


}
