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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.Genre;
import com.groep3.pathedeapp.domain.List;
import com.groep3.pathedeapp.domain.LoadedReviews;
import com.groep3.pathedeapp.domain.Movie;
import com.groep3.pathedeapp.domain.Review;
import com.groep3.pathedeapp.domain.UserAuthenticate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetail extends AppCompatActivity {
    private String TAG = MovieDetail.class.getSimpleName();
    private Movie movie;
    private final LinkedList<Review> mReviewList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private ReviewList mAdapter;
    private int pageNumber = 1;
    private int totalPages = 1;
    private ArrayList<Genre> genres;
    private ArrayList<String> genreNames = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        setAdapter();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && totalPages != pageNumber) {
                    pageNumber++;

                    getAllReviews();
                }
            }
        });

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
        ImageButton listButton = (ImageButton) findViewById(R.id.detail_list_button);
        TextView score = (TextView) findViewById(R.id.detail_rating);
        TextView genre = (TextView) findViewById(R.id.detail_genre);
        TextView description = (TextView) findViewById(R.id.detail_description);
        TextView length = (TextView) findViewById(R.id.detail_length);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Movie> call = apiInterface.getMovie(Integer.parseInt(getIntent().getStringExtra("movieId")), "11db3143a380ada0de96fe9028cbc905");

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();
                name.setText(movie.getTitle());
                score.setText(movie.getVoteAverage().toString());
//                             genre.setText( movie.getGenreIds().toString());
                genres = (ArrayList<Genre>) movie.genres();
                for (int i = 0; i < genres.size(); i++) {
                    genreNames.add(genres.get(i).getName());
                }
                genre.setText(genreNames.toString().replace("[", "").replace("]",  ""));

                description.setText(movie.getOverview());
                length.setText(movie.getRuntime().toString());
                if (movie.getPosterPath() != null) {
                    Picasso.with(getApplicationContext())
                            .load("https://image.tmdb.org/t/p/w500/" + movie.getPosterPath())
                            .into(image);
                }

                Log.d(TAG, movie.getTitle());
                Log.d(TAG, response.body().toString());
                Log.d(TAG, response.toString());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
            }
        });

        getAllReviews();

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

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Moet nog gecheckt worden of gebruiker is ingelogd met session id of niet. Kan alleen lijst aangemaakt worden met login session id.
                UserAuthenticate guestSession = new UserAuthenticate();
                if(LoginActivity.SESSION_ID == LoginActivity.GUEST_SESSION_ID) {
                    //Toast bericht dat er niet ingelogd is
                    Toast.makeText(getApplicationContext(), "You're not logged in", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(view.getContext(), ListActivity.class);
                    view.getContext().startActivity(intent);
                }
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

    private void setAdapter() {
        mRecyclerView = findViewById(R.id.reviewView);
        mAdapter = new ReviewList(this, mReviewList);
        mRecyclerView.setAdapter(mAdapter);
        int gridColumnCount = 1;

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
    }

    private void getAllReviews() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoadedReviews> reviewCall = apiInterface.getReview(Integer.parseInt(getIntent().getStringExtra("movieId")), "11db3143a380ada0de96fe9028cbc905", pageNumber);

        reviewCall.enqueue(new Callback<LoadedReviews>() {
            @Override
            public void onResponse(Call<LoadedReviews> call, Response<LoadedReviews> response) {
                LoadedReviews reviews = response.body();
                totalPages = reviews.getTotalPages();
                mReviewList.addAll(reviews.getResults());
                Log.d("reviewListreviews", response.toString());

                for (int i = 0; i < mReviewList.size(); i++) {
                    Review review = mReviewList.get(i);

                    Log.d(TAG, review.getAuthor());
                }
                setAdapter();
            }

            @Override
            public void onFailure(Call<LoadedReviews> call, Throwable t) {
            }
        });
    }

}

