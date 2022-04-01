package com.groep3.pathedeapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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
        TextView name = (TextView) findViewById(R.id.detail_movieName);
        ImageView image = (ImageView) findViewById(R.id.detail_movie_cover);
        ImageButton shareButton = (ImageButton) findViewById(R.id.detail_share_button);
        TextView score = (TextView) findViewById(R.id.detail_rating);
        TextView genre = (TextView) findViewById(R.id.detail_genre);
        TextView description = (TextView) findViewById(R.id.detail_description);

        //put view values on screen
        Log.i(TAG, "Giving views data");
        name.setText(getIntent().getStringExtra("movieName"));
        score.setText(getIntent().getStringExtra("movieRating"));
        genre.setText(getIntent().getStringExtra("movieGenre"));
        description.setText(getIntent().getStringExtra("movieDescription"));


        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500/" + getIntent().getStringExtra("movie_cover"))
                .into(image);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                intent.putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/movie/" + getIntent().getStringExtra("movieId"));
                startActivity(Intent.createChooser(intent, "Share URL"));
            }
        });

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
