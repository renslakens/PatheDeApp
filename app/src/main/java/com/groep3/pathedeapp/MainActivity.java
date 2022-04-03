package com.groep3.pathedeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    private String descending = "desc";
    private String sortBy = "popularity.";
    private String call = sortBy + descending;

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

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    pageNumber++;
                    call = sortBy + descending;
                    getAllMovies(call);
                }
            }
        });


        Spinner genreSpinner = (Spinner) findViewById(R.id.genre_spinner);
        ArrayAdapter<CharSequence> sortByAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_sort, android.R.layout.simple_spinner_item);
        sortByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(sortByAdapter);
        genreSpinner.setOnItemSelectedListener(new sortListener());


        Spinner ratingSpinner = (Spinner) findViewById(R.id.rating_spinner);
        ArrayAdapter<CharSequence> modeAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_mode, android.R.layout.simple_spinner_item);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingSpinner.setAdapter(modeAdapter);
        ratingSpinner.setOnItemSelectedListener(new modeListener());
    }


    @Override
    public void onRefresh() {

        setAdapter();
        call = sortBy + descending;
        getAllMovies(call);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void setAdapter() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new MovieList(this, mMovieList);
        mRecyclerView.setAdapter(mAdapter);
        int gridColumnCount = 1;

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
    }


    private void getAllMovies(String sortBy) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<LoadedMovies> call = apiInterface.getMovies("11db3143a380ada0de96fe9028cbc905", pageNumber, sortBy);

        call.enqueue(new Callback<LoadedMovies>() {
            @Override
            public void onResponse(Call<LoadedMovies> call, Response<LoadedMovies> response) {
                LoadedMovies movies = response.body();
                mMovieList.addAll(movies.getResults());
                Log.d("movieListMovies", response.toString());
                Log.d("MovieListMovies", mMovieList.toString());
                mAdapter.setMovieList(mMovieList);
            }

            @Override
            public void onFailure(Call<LoadedMovies> call, Throwable t) {
                Log.e("MainActivity", t.toString());
            }
        });

    }

    private class sortListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            mMovieList.clear();
            switch (position) {
                case 0:
                    sortBy = "popularity.";
                    Log.d("test", "test");
                    break;
                case 1:
                    sortBy = "release_date.";
                    break;
                case 2:
                    sortBy = "revenue.";
                    break;
                case 3:
                    sortBy = "primary_release_date.";
                    break;
                case 4:
                    sortBy = "original_title.";
                    break;
                case 5:
                    sortBy = "vote_average.";
                    break;
                case 6:
                    sortBy = "vote_count.";
                    break;

            }
            call = sortBy + descending;
            getAllMovies(call);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


    private class modeListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            Object value = adapterView.getItemAtPosition(position);
            mMovieList.clear();
            switch (position) {
                case 0:
                    descending = "desc";
                    Log.d("test", "test");
                    break;
                case 1:
                    descending = "asc";
                    break;
            }
            call = sortBy + descending;
            getAllMovies(call);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}