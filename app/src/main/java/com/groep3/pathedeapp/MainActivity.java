package com.groep3.pathedeapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.LoadedMovies;
import com.groep3.pathedeapp.domain.Movie;
import com.groep3.pathedeapp.presentation.ChooseFilterDialog;
import com.groep3.pathedeapp.presentation.FilterOption;
import com.groep3.pathedeapp.presentation.MovieList;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, ChooseFilterDialog.OnInputListenerDialog, FilterOption.OnInputListener {
    private String descending = "desc";
    private String sortBy = "popularity.";
    private String call = sortBy + descending;
    Context context;
    private String currentQuery;
    private Spinner genreSpinner;
    private Spinner ratingSpinner;
    private String text = "test";
    private Integer voteCount = null;
    private Integer year = null;
    private Integer voteAverage = null;
    private String originalLanguage = null;
    private Integer filterMode = null;
    private ImageView filterButton;
    private String apiKey = "11db3143a380ada0de96fe9028cbc905";

    private SearchView editsearch;

    private final LinkedList<Movie> mMovieList = new LinkedList<>();
    Integer pageNumber = 1;

    private MovieList mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private final String TAG = "MainActivity";

    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

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
                    if (currentQuery == null) {
                        pageNumber++;
                        call = sortBy + descending;
                        getAllMovies(call);
                    } else {
                        pageNumber++;
                        searchMovie(currentQuery);
                    }
                }
            }
        });

        genreSpinner = (Spinner) findViewById(R.id.genre_spinner);
        ArrayAdapter<CharSequence> sortByAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_sort, android.R.layout.simple_spinner_item);
        sortByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(sortByAdapter);
        genreSpinner.setOnItemSelectedListener(new sortListener());

        ratingSpinner = (Spinner) findViewById(R.id.genre_mode_spinner);
        ArrayAdapter<CharSequence> genreModeAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_mode, android.R.layout.simple_spinner_item);
        genreModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingSpinner.setAdapter(genreModeAdapter);
        ratingSpinner.setOnItemSelectedListener(new sortListener());

        filterButton = (ImageView) findViewById(R.id.filter_button);


        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterReleaseYear(1);
            }
        });

        editsearch = (SearchView) findViewById(R.id.search_bar);
        editsearch.setOnQueryTextListener(this);


        context = getApplicationContext();
    }

    @Override
    public void onRefresh() {

        if (genreSpinner.getVisibility() == mRecyclerView.GONE) {
            setAdapter();
            mMovieList.clear();
            searchMovie(currentQuery);
        } else {
            setAdapter();
            call = sortBy + descending;
            mMovieList.clear();
            getAllMovies(call);
        }


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
        mSwipeRefreshLayout.setRefreshing(true);

        Call<LoadedMovies> call = apiInterface.getMovies(apiKey, pageNumber, sortBy, voteCount, year, voteAverage, originalLanguage);

        call.enqueue(new Callback<LoadedMovies>() {
            @Override
            public void onResponse(Call<LoadedMovies> call, Response<LoadedMovies> response) {
                LoadedMovies movies = response.body();
                mMovieList.addAll(movies.getResults());
                Log.d("movieListMovies", response.toString());
                Log.d("MovieListMovies", mMovieList.toString());
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setMovieList(mMovieList);
            }

            @Override
            public void onFailure(Call<LoadedMovies> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }

    private void searchMovie(String query) {
        currentQuery = query;
        mSwipeRefreshLayout.setRefreshing(true);

        Call<LoadedMovies> call = apiInterface.searchMovies(apiKey, query, pageNumber);

        call.enqueue(new Callback<LoadedMovies>() {
            @Override
            public void onResponse(Call<LoadedMovies> call, Response<LoadedMovies> response) {
                LoadedMovies movies = response.body();
                mMovieList.addAll(movies.getResults());
                Log.d("movieListMovies", response.toString());
                Log.d("MovieListMovies", mMovieList.toString());
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setMovieList(mMovieList);
            }

            @Override
            public void onFailure(Call<LoadedMovies> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        SearchView simpleSearchView = (SearchView) findViewById(R.id.search_bar); // inititate a search view
        CharSequence query = simpleSearchView.getQuery(); // get the query string currently in the text field
        Log.d("test", s);
        mMovieList.clear();
        searchMovie(s);
        genreSpinner.setVisibility(mRecyclerView.GONE);
        ratingSpinner.setVisibility(mRecyclerView.GONE);


        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.isEmpty()) {
            call = sortBy + descending;
            mMovieList.clear();
            s = null;
            getAllMovies(call);
            genreSpinner.setVisibility(mRecyclerView.VISIBLE);
            ratingSpinner.setVisibility(mRecyclerView.VISIBLE);
        }
        return false;
    }

    @Override
    public void sendInput(Integer input) {
        this.filterMode = input;
        Log.d(TAG, String.valueOf(filterMode));

        if (filterMode == 9) {
            voteCount = null;
            year = null;
            voteAverage = null;
            originalLanguage = null;
            pageNumber = 1;
            call = sortBy + descending;
            mMovieList.clear();
            getAllMovies(call);
        }


    }

    @Override
    public void sendInput(String input) {
        switch (filterMode) {
            case 0:
                voteCount = Integer.valueOf(input);
                break;
            case 1:
                year = Integer.valueOf(input);
                break;
            case 2:
                voteAverage = Integer.valueOf(input);
                break;
            case 3:
                originalLanguage = input;
                break;
        }
        pageNumber = 1;
        call = sortBy + descending;
        mMovieList.clear();
        getAllMovies(call);
    }

    //Filteren op populariteit, release date, revenue, primary release date, original title, vote average en vote count
    private class sortListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            mMovieList.clear();
            switch (position) {
                case 0:
                    sortBy = "popularity.";
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

    public void filterReleaseYear(Integer mode) {
        DialogFragment newFragment = new ChooseFilterDialog(mode);
        newFragment.show(getSupportFragmentManager(), "filterPicker");
    }

}