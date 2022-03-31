package com.groep3.pathedeapp.dataacces;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.groep3.pathedeapp.domain.Movie;
import com.groep3.pathedeapp.presentation.MovieList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FetchMovie extends AsyncTask<String, Void, String> {
    private ArrayList<Movie> mMovies;
    private final MovieList mAdapter;
    private final Context mContext;
    private final String TAG = "FetchMeal";

    public FetchMovie(Context context) {
        this.mContext = context;
        mMovies = new ArrayList<Movie>();
        mAdapter = new MovieList(context, mMovies);
        Log.d(TAG, "Made Fetchmeal class");
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.i(TAG, "started doInBackground");
        return NetworkUtils.getInfo();
    }

    protected void onPostExecute(String jsonString) {
        try {
            mMovies.clear();
            JSONObject obj = new JSONObject(jsonString);
            JSONArray mealArray = obj.getJSONArray("results");
            Log.i(TAG, "Starting JSON operation");
            for (int i = 0; i < mealArray.length(); i++) {
                JSONObject movieInfo = mealArray.getJSONObject(i);


                mMovies.add(new Movie(movieInfo.getString("title"), movieInfo.getString("poster_path")));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onPostExecute(jsonString);
        Toast.makeText(mContext,
                mMovies.size() + "Movies loaded",
                Toast.LENGTH_SHORT)
                .show();
        mAdapter.notifyDataSetChanged();
    }


    public MovieList getAdapter() {
        Log.d(TAG, "returned MovieList Adapter");
        return mAdapter;
    }
}

