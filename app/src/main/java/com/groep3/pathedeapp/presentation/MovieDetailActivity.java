package com.groep3.pathedeapp.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.Genre;
import com.groep3.pathedeapp.domain.LoadedReviews;
import com.groep3.pathedeapp.domain.LoadedVideos;
import com.groep3.pathedeapp.domain.Movie;
import com.groep3.pathedeapp.domain.Rating;
import com.groep3.pathedeapp.domain.Review;
import com.groep3.pathedeapp.domain.UserAuthenticate;
import com.groep3.pathedeapp.domain.Video;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailActivity extends AppCompatActivity implements FilterOption.OnInputListener {
    private String TAG = MovieDetailActivity.class.getSimpleName();
    private Movie movie;
    private final LinkedList<Review> mReviewList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private ReviewList mAdapter;
    private int pageNumber = 1;
    private int totalPages = 1;
    private ArrayList<Genre> genres;
    private ArrayList<String> genreNames = new ArrayList<String>();
    private ArrayList<Video> videos;
    private LoadedVideos loadedVideos;
    private String trailerLink;
    private String sessionId = null;
    private String guestSessionId = null;
    private Number rating;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
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
        Button trailer = (Button) findViewById(R.id.detail_trailer);
        ImageButton rateMovie = (ImageButton) findViewById(R.id.detail_favorite_button);


        Call<Movie> call = apiInterface.getMovie(Integer.parseInt(getIntent().getStringExtra("movieId")), "11db3143a380ada0de96fe9028cbc905");

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();
                name.setText(movie.getTitle());
                score.setText(movie.getVoteAverage().toString());
                genres = (ArrayList<Genre>) movie.genres();
                for (int i = 0; i < genres.size(); i++) {
                    genreNames.add(genres.get(i).getName());
                }
                genre.setText(genreNames.toString().replace("[", "").replace("]", ""));

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
                Log.d(TAG, getIntent().getStringExtra("session_id"));
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        if (getIntent().getBooleanExtra("logged_in", true)) {
            sessionId = getIntent().getStringExtra("session_id");
        } else {
            guestSessionId = getIntent().getStringExtra("session_id");
        }

        rateMovie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new FilterOption(4);
                newFragment.show(getSupportFragmentManager(), TAG);

            }
        });

        Call<LoadedVideos> videosCall = apiInterface.getVideos(Integer.parseInt(getIntent().getStringExtra("movieId")), "11db3143a380ada0de96fe9028cbc905");

        videosCall.enqueue(new Callback<LoadedVideos>() {


            @Override
            public void onResponse(Call<LoadedVideos> call, Response<LoadedVideos> response) {
                loadedVideos = response.body();
                Log.d("videos", loadedVideos.toString());
                videos = (ArrayList<Video>) loadedVideos.getVideos();
                String type = "wrong";
                String site = "wrong";
                if (videos.size() > 1) {
                    Log.d("size", String.valueOf(videos.size()));
                    for (int i = 0; i < videos.size(); i++) {
                        type = videos.get(i).getType();
                        site = videos.get(i).getSite();
//                        https://www.youtube.com/watch?v=
                        trailerLink = "vnd.youtube://" + videos.get(i).getKey();
                        if (type.equals("Trailer") && site.equals("YouTube")) {
                            Log.d(TAG, trailerLink.toString());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoadedVideos> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        getAllReviews();

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(trailerLink == null)) {
                    Log.d(TAG, "starting youtube");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerLink));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_trailers, Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                if (getIntent().getBooleanExtra("logged_in", false)) {
                    Intent intent = new Intent(view.getContext(), ListActivity.class);
                    intent.putExtra("logged_in", getIntent().getBooleanExtra("logged_in", true));
                    intent.putExtra("session_id", getIntent().getStringExtra("session_id"));
                    view.getContext().startActivity(intent);
                } else {
                    //Toast bericht dat er niet ingelogd is
                    Toast.makeText(getApplicationContext(), R.string.not_logged_in, Toast.LENGTH_LONG).show();
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
                Log.d(TAG, response.toString());

                for (int i = 0; i < mReviewList.size(); i++) {
                    Review review = mReviewList.get(i);

                    Log.d(TAG, review.getAuthor());
                }
                setAdapter();
            }

            @Override
            public void onFailure(Call<LoadedReviews> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void sendInput(String input) {

        rating = Double.valueOf(input);

        Call<Rating> ratingCall = apiInterface.rateMovie(movie.getId(), "11db3143a380ada0de96fe9028cbc905", guestSessionId, sessionId, rating);

        ratingCall.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
                if (response.isSuccessful()) {
                    Log.d("Rating successful", response.body().toString() + response.message());
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.invalid_input_error,
                            Toast.LENGTH_SHORT).show();
                    try {
                        Log.d("Error occurred", "failure " + response.headers() + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        Log.d(TAG, rating.toString());

    }


}

