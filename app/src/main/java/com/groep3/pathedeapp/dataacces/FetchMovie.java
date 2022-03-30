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

                //get date
//                String date = mealInfo.getString("dateTime").toString();
//                String serveDate = null;
//                try {
//                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                    Date tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//                    serveDate = String.valueOf(dateFormat.format(tempDate));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                //make cook
//                JSONObject objcook = new JSONObject(mealInfo.getString("cook"));
//                String cookNameString = objcook.getString("firstName")+ " " + objcook.getString("lastName");
//                Cook cook = new Cook(
//                        cookNameString,
//                        objcook.getString("city"),
//                        objcook.getString("street"),
//                        objcook.getString("phoneNumber"),
//                        objcook.getString("emailAdress"));

                mMovies.add(new Movie(movieInfo.getString("title")));
//
//                //make meal
//                mMovies.add(new Meal(
//                        mealInfo.getString("name"),
//                        mealInfo.getString("description"),
//                        mealInfo.getString("price"),
//                        mealInfo.getString("imageUrl"),
//                        mealInfo.getInt("id"),
//                        mealInfo.getBoolean("isVega"),
//                        mealInfo.getBoolean("isVegan"),
//                        serveDate,
//                        mealInfo.getString("allergenes"),
//                        cook,
//                        mealInfo.getBoolean("isToTakeHome"),
//                        mealInfo.getBoolean("isActive")));
//                Log.d(TAG, "Created meal" + mealInfo.getInt("id"));
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

