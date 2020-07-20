package com.example.eman214.booklisting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public EditText mEdit;
    private BookAdapter mAdapter;
    public static final String LOG_TAG = BookActivity.class.getName();
    public static final String GOOGLE_BOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";
    public int BOOK_LOADER_ID = 1;
    public String searchInput;
    private TextView mEmptyTextView;
    private ProgressBar mProgressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        final ListView bookListView = findViewById(R.id.list);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        mEmptyTextView = findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyTextView);

        mProgressbar = findViewById(R.id.progress);
        mProgressbar.setVisibility(View.GONE);

        mEdit = findViewById(R.id.search_bar);

        final ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        Button btn = findViewById(R.id.search_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(mEdit);
                searchInput = mEdit.getText().toString();
                searchInput = searchInput.replace(" ", "+");
                if (searchInput.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter something to search", Toast.LENGTH_LONG).show();
                }
                //Check to see if there is an internet connection and if so initialize the loaders
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                    if (capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))) {
                        startLoader();
                    } else {
                        // Otherwise, display error
                        noInternet();
                    }
                } else {
                    //Used for older phones running API below 23
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    if (activeNetwork != null && activeNetwork.isConnected()) {
                        startLoader();
                    } else {
                        noInternet();
                    }
                }
            }
        });

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book currentBook = mAdapter.getItem(position);
                //Covert String url into a URI object and create a new intent to view that URL
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(currentBook.getUrl()));

                // Verify that the intent will resolve to an activity
                if (websiteIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(websiteIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "No application can handle this request."
                            + " Please install a web browser", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.e(LOG_TAG, "Test: onCreateLoader() called...");
        mProgressbar.setVisibility(View.VISIBLE);
        return new BookLoader(this, GOOGLE_BOOKS_REQUEST_URL + searchInput + "&maxResults=10");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {
        Log.e(LOG_TAG, "Test: onLoadFinished() called...");
        mProgressbar.setVisibility(View.GONE);
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
        mEmptyTextView.setText(R.string.no_data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
        Log.e(LOG_TAG, "Test: onLoaderReset() called()...");
        mAdapter.clear();
    }

    public void closeKeyboard(View v) {
        try {
            InputMethodManager editTextInput = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            editTextInput.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            Log.e(LOG_TAG, "closeKeyboard: " + e);
        }
    }

    public void startLoader() {
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = LoaderManager.getInstance(BookActivity.this);
        loaderManager.restartLoader(BOOK_LOADER_ID, null, BookActivity.this);
    }

    public void noInternet() {
        mAdapter.clear();
        mProgressbar.setVisibility(View.GONE);
        mEmptyTextView.setText(R.string.no_internet_connection);
    }
}