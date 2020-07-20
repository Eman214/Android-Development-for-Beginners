package com.example.eman214.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        //Check if there is a view that can be reused, if not inflate a new one
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //Get current item position
        Book currentBook = getItem(position);

        //Find the view with the ID image and set the new image

        //Find the view with the ID title and set the new title
        TextView title = listItemView.findViewById(R.id.title);
        assert currentBook != null;
        title.setText(currentBook.getTitle());

        //Find the view with the ID title and set the new title
        TextView author = listItemView.findViewById(R.id.author);
        author.setText(currentBook.getAuthors());

        //Find the view with the ID description and set the new description
        TextView description = listItemView.findViewById(R.id.description);
        description.setText(currentBook.getDescription());

        RatingBar rating = listItemView.findViewById(R.id.rating_bar);
        if (!currentBook.getRating().equals("")) {
            rating.setRating((Float.parseFloat(currentBook.getRating())));
        } else {
            rating.setVisibility(View.GONE);
        }
        return listItemView;
    }
}
