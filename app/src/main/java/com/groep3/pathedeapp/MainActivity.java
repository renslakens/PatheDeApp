package com.groep3.pathedeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.LoadedMovies;
import com.groep3.pathedeapp.domain.Movie;
import com.groep3.pathedeapp.presentation.MovieList;


import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private final LinkedList<Movie> mMovieList = new LinkedList<>();
    Integer pageNumber = 1;

    private MovieList mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Log the start of the onCreate() method.
        Log.d(TAG, "-------");
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);
        setAdapter();
        getAllMovies();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    pageNumber++;
                    getAllMovies();
                }
            }
        });



        Spinner genreSpinner = (Spinner) findViewById(R.id.genre_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_test, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);


        Spinner ratingSpinner = (Spinner) findViewById(R.id.rating_spinner);

        ratingSpinner.setAdapter(adapter);
    }


    @Override
    public void onRefresh() {

        setAdapter();
        getAllMovies();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void setAdapter() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new MovieList(this, mMovieList);
        mRecyclerView.setAdapter(mAdapter);
        int gridColumnCount = 1;

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
    }


    private void getAllMovies() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<LoadedMovies> call = apiInterface.getMovies("11db3143a380ada0de96fe9028cbc905", pageNumber);

        call.enqueue(new Callback<LoadedMovies>() {
            @Override
            public void onResponse(Call<LoadedMovies> call, Response<LoadedMovies> response) {
                LoadedMovies movies = response.body();
                mMovieList.addAll(movies.getResults());
                Log.d("MovieListMovies", mMovieList.toString());
                mAdapter.setMovieList(mMovieList);
            }

            @Override
            public void onFailure(Call<LoadedMovies> call, Throwable t) {
                Log.e("MainActivity", t.toString());
            }
        });

    }
}