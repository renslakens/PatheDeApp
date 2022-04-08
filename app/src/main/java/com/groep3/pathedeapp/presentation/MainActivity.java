package com.groep3.pathedeapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.Genre;
import com.groep3.pathedeapp.domain.LoadedGenres;
import com.groep3.pathedeapp.domain.LoadedMovies;
import com.groep3.pathedeapp.domain.Movie;

import java.util.LinkedList;
import java.util.Locale;

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
    private Integer voteCount = null;
    private Integer year = null;
    private Integer voteAverage = null;
    private String originalLanguage = null;
    private Integer filterMode = null;
    private ImageView filterButton;
    private String genre = null;
    private Integer genreSize = null;
    private final LinkedList<Genre> genres = new LinkedList<>();
    private final String apiKey = "11db3143a380ada0de96fe9028cbc905";

    private SearchView editsearch;

    private final LinkedList<Movie> mMovieList = new LinkedList<>();
    Integer pageNumber = 1;

    private MovieList mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private final String TAG = MainActivity.class.getSimpleName();

    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Log the start of the onCreate() method.

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
        ratingSpinner.setOnItemSelectedListener(new modeListener());

        filterButton = (ImageView) findViewById(R.id.filter_button);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterList(1);
            }
        });

        editsearch = (SearchView) findViewById(R.id.search_bar);
        editsearch.setOnQueryTextListener(this);

        context = getApplicationContext();
        getGenres();

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
        Log.d(TAG, getIntent().getStringExtra("session_id"));
        mAdapter = new MovieList(this, mMovieList, getIntent().getStringExtra("session_id"), getIntent().getBooleanExtra("logged_in", true));
        mRecyclerView.setAdapter(mAdapter);
        int gridColumnCount = 1;

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
    }


    private void getAllMovies(String sortBy) {
        mSwipeRefreshLayout.setRefreshing(true);

        Call<LoadedMovies> call = apiInterface.getMovies(apiKey, pageNumber, sortBy, voteCount, year, voteAverage, originalLanguage, genre);

        call.enqueue(new Callback<LoadedMovies>() {
            @Override
            public void onResponse(Call<LoadedMovies> call, Response<LoadedMovies> response) {
                LoadedMovies movies = response.body();
                mMovieList.addAll(movies.getResults());
                Log.d(TAG, "response = " + response);
                Log.d(TAG, "Movies = " + mMovieList);
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
                Log.d(TAG, "response = " + response);
                Log.d(TAG, "Movies = " + mMovieList);
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setMovieList(mMovieList);
                if (mMovieList.isEmpty()) {
                    Toast.makeText(context, R.string.could_not_find_movies, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoadedMovies> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mRecyclerView.stopScroll();
        pageNumber = 1;
        Log.d(TAG, s);
        setAdapter();
        mMovieList.clear();
        searchMovie(s);
        genreSpinner.setVisibility(mRecyclerView.GONE);
        ratingSpinner.setVisibility(mRecyclerView.GONE);
        filterButton.setVisibility(mRecyclerView.GONE);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mRecyclerView.stopScroll();
        if (s.isEmpty()) {
            pageNumber = 1;
            setAdapter();
            call = sortBy + descending;
            mMovieList.clear();
            s = null;
            getAllMovies(call);
            genreSpinner.setVisibility(mRecyclerView.VISIBLE);
            ratingSpinner.setVisibility(mRecyclerView.VISIBLE);
            filterButton.setVisibility(mRecyclerView.VISIBLE);
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
            genre = null;
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
            case 5:
                for (int i = 0; i < genreSize; i++) {
                    if (input.toLowerCase().equals(genres.get(i).getName().toLowerCase(Locale.ROOT))) {
                        genre = genres.get(i).getId().toString();
                        break;
                    }
                }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.lists_button) {
            Boolean logged_in = getIntent().getBooleanExtra("logged_in", false);
            if (logged_in) {
                Intent intent = new Intent(context, ListActivity.class);
                intent.putExtra("session_id", getIntent().getStringExtra("session_id"));
                intent.putExtra("logged_in", getIntent().getBooleanExtra("logged_in", true));
                startActivity(intent);
                return true;
            } else {
                Toast.makeText(getApplicationContext(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
                return false;
            }


        } else if (id == R.id.log_out) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.theme_button) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void filterList(Integer mode) {
        DialogFragment newFragment = new ChooseFilterDialog(mode);
        newFragment.show(getSupportFragmentManager(), TAG);
    }

    private void getGenres() {
        mSwipeRefreshLayout.setRefreshing(true);

        Call<LoadedGenres> call = apiInterface.getGenres(apiKey);

        call.enqueue(new Callback<LoadedGenres>() {
            @Override
            public void onResponse(Call<LoadedGenres> call, Response<LoadedGenres> response) {
                LoadedGenres allGenres = response.body();
                genres.addAll(allGenres.getGenres());
                genreSize = allGenres.getSize();

            }

            @Override
            public void onFailure(Call<LoadedGenres> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}