package com.groep3.pathedeapp.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.groep3.pathedeapp.R;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {
    private String TAG = "MovieDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);


        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        //makes all the views
        Log.i(TAG, "Creating views");
        TextView name = (TextView) findViewById(R.id.movieName);
        ImageView image = (ImageView) findViewById(R.id.detail_movie_cover);

        //put view values on screen
        Log.i(TAG, "Giving views data");
        name.setText(getIntent().getStringExtra("movieName"));

        Log.d(TAG, "Attempting to load image" + getIntent().getStringExtra("image_resource"));
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500/" + getIntent().getStringExtra("movie_cover"))
                .into(image);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Log.d(TAG, "Going back to main menu");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
