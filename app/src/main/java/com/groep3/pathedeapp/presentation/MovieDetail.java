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

import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.LoadedMovies;
import com.groep3.pathedeapp.domain.Movie;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetail extends AppCompatActivity {
    private String TAG = "MovieDetail";
    private Movie movie;



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
        TextView length = (TextView) findViewById(R.id.detail_length);

        //
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Movie> call = apiInterface.getMovie(Integer.parseInt(getIntent().getStringExtra("movieId")),  "bce3e84f67721f9e61473a5c397a0bf1");

        call.enqueue(new Callback<Movie>() {
                         @Override
                         public void onResponse(Call<Movie> call, Response<Movie> response) {
                             movie = response.body();
                             name.setText(movie.getTitle());
                             score.setText(movie.getVoteAverage().toString());
//                             genre.setText( movie.getGenreIds().toString());
                             description.setText(movie.getOverview());
                             length.setText(movie.getRuntime().toString());
                             Picasso.with(getApplicationContext())
                                     .load("https://image.tmdb.org/t/p/w500/" + movie.getPosterPath())
                                     .into(image);

                             Log.d("test", movie.getTitle());
                             Log.d("test", response.body().toString());
                             Log.d("test", response.toString());
                         }

                         @Override
                         public void onFailure(Call<Movie> call, Throwable t) {

                         }
                     });
            //



        //put view values on screen
        Log.i(TAG, "Giving views data");
//        getMovie(Integer.parseInt(getIntent().getStringExtra("movieId")));
//        name.setText(movie.getTitle());
//        name.setText(getIntent().getStringExtra("movieName"));
//        score.setText(getIntent().getStringExtra("movieRating"));
//        genre.setText(getIntent().getStringExtra("movieGenre"));
//        description.setText(getIntent().getStringExtra("movieDescription"));



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

//    private void getMovie(int movieId) {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//
//        Call<Movie> call = apiInterface.getMovie(  "bce3e84f67721f9e61473a5c397a0bf1");
//
//        call.enqueue(new Callback<Movie>() {
//            @Override
//            public void onResponse(Call<Movie> call, Response<Movie> response) {
//                movie = response.body();
////                name.setText(movie.getTitle());
//                Log.d("test", movie.getTitle());
//                Log.d("test", response.body().toString());
//                Log.d("test", response.toString());
//            }
//
//            @Override
//            public void onFailure(Call<Movie> call, Throwable t) {
//
//            }
//
////            @Override
////            public void onResponse(Call<LoadedMovies> call, Response<LoadedMovies> response) {
////                movie = response.body();
////                LoadedMovies movies = response.body();
////                mMovieList.addAll(movies.getResults());
////                Log.d("MovieListMovies", mMovieList.toString());
////                mAdapter.setMovieList(mMovieList);
////            }
////
////            @Override
////            public void onFailure(Call<LoadedMovies> call, Throwable t) {
////                Log.e("MainActivity", t.toString());
////            }
//        });

    }

