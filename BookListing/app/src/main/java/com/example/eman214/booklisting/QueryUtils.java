package com.example.eman214.booklisting;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }


    public static List<Book> fetchBookInfo(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error making HTTP request");
        }
        return extractEarthquakes(jsonResponse);
    }

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating url", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to server");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why the
                // makeHttpRequest(URL url) method specifies than an IOException could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader buffer = new BufferedReader(inputStreamReader);
            String line = buffer.readLine();
            while (line != null) {
                output.append(line);
                line = buffer.readLine();
            }
        }
        return output.toString();
    }

    public static List<Book> extractEarthquakes(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<Book> books = new ArrayList<>();
        try {
            JSONObject jsonRootObject = new JSONObject(jsonResponse);
            JSONArray bookArray = jsonRootObject.optJSONArray("items");
            if (bookArray != null) {
                for (int i = 0; i < bookArray.length(); i++) {
                    JSONObject currentBook = bookArray.getJSONObject(i);
                    JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                    String title = volumeInfo.optString("title");
                    String[] authors = new String[]{};

                    //For books with multiple authors we read in each one from the JSONArray then
                    //assign them to the authors String array.
                    JSONArray authorJsonArray = volumeInfo.optJSONArray("authors");
                    if (authorJsonArray != null) {
                        ArrayList<String> authorList = new ArrayList<>();
                        for (int j = 0; j < authorJsonArray.length(); j++) {
                            authorList.add(authorJsonArray.get(j).toString());
                        }
                        authors = authorList.toArray(new String[0]);
                    }

                    String description = volumeInfo.optString("description");
                    String url = volumeInfo.optString("previewLink");
                    String rating = volumeInfo.optString("averageRating");

                    Book book = new Book(title, authors, description, url, rating);
                    books.add(book);
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing the book JSON results");
        }
        return books;
    }
}
