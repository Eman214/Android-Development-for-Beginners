package com.example.android.quakereport;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return an {@link Earthquake} object to represent
     * a list of earthquakes.
     */
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        Log.e(LOG_TAG, "Test: fetchEarthquakeData called()...");
        //Create URL object
        URL url = createUrl(requestUrl);

        //Try to preform the HTTP request to the url and store the JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error making HTTP request");
        }

        //Extract the relevant fields from the JSON response and crate a list of {@link Earthquake}s
        List<Earthquake> earthquakes = extractEarthquakes(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }
    /**
     * Returns a URL object from a given string
     */
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem with creating URL", e);
        }
        return url;
    }

    /**
     *
     * Make a HTTP request to the given url and return a String response
     */
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

            //If the request was successful then read and parse the response from the input stream
            //If the request failed log the error response code
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String containing the JSON response
     */
    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader buffer = new BufferedReader(inputStreamReader);
            String line = buffer.readLine();
            while (line != null) {
                output.append(line);
                line = buffer.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Earthquake} objects created from parsing the JSON response
     */
    public static List<Earthquake> extractEarthquakes(String jsonResponse) {
        //If the string is empty return early
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that can have earthquakes added to it
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the USGS_REQUEST_URL. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            //Convert USGS_REQUEST_URL String into a JSONObject
            //then extract the JSONArray with the key “features”.
            JSONObject jsonRootObject = new JSONObject(jsonResponse);
            JSONArray earthquakeArray = jsonRootObject.optJSONArray("features");

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            assert earthquakeArray != null;
            for (int i = 0; i < earthquakeArray.length(); i++) {
                //Select the earthquake JSONObject at position i then
                // extract the JSONObject with the key “properties”.
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");

                //Extract the magnitude, place, and time from the JSONArray.
                double mag = properties.optDouble("mag");
                String location = properties.optString("place");
                long time = properties.optLong("time");
                String url = properties.optString("url");

                //Create a new Earthquake object using the extracted properties
                // and add it .to the ArrayList
                Earthquake earthquake = new Earthquake(mag, location, time, url);
                earthquakes.add(earthquake);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}