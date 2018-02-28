package com.example.hesham.moves.async;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/28/18.
 */

public class MovieTask extends AsyncTask<String, Void, List<Movies>> {

    private final MyCallback movieTaskCallback;
    Context context;

    public MovieTask(MyCallback movieTaskCallback, Context context) {
        this.movieTaskCallback = movieTaskCallback;
        this.context=context;

    }


    private List<Movies> getMoviesFromJson(String movieJsonString) throws JSONException {
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";


        if (movieJsonString == null || "".equals(movieJsonString)) {
            return null;
        }

        JSONObject jsonObjectMovie = new JSONObject(movieJsonString);
        JSONArray jsonArrayMovies = jsonObjectMovie.getJSONArray("results");

        List<Movies> moviesList= new ArrayList<>();

        for (int i = 0; i < jsonArrayMovies.length(); i++) {
            JSONObject object = jsonArrayMovies.getJSONObject(i);

            Movies movies =new Movies(
                    object.getString(ORIGINAL_TITLE),
                    object.getString(POSTER_PATH),
                    object.getString(OVERVIEW),
                    object.getString(VOTE_AVERAGE),
                    object.getString(RELEASE_DATE)
            );

            moviesList.add(movies);
        }
        return moviesList;

    }

    @Override
    protected List<Movies> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }
        final String API_KEY = "api_key";

        HttpURLConnection urlConnection = null;
        String movieJsonString = null;
        BufferedReader reader = null;

        Uri uri = Uri.parse(params[0]).buildUpon()
                .appendQueryParameter(API_KEY,"28f81313599c7074d6380330fe1dca22")
                .build();

        try {
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            movieJsonString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            return getMoviesFromJson(movieJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Movies> movies) {
        movieTaskCallback.updateAdapter(movies);
    }
}

