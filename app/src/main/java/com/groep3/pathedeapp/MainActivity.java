package com.groep3.pathedeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.groep3.pathedeapp.dataacces.FetchMovie;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private FetchMovie mMovie = new FetchMovie(this);
    private RecyclerView.Adapter mAdapter = mMovie.getAdapter();
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
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        // maakt recyclerview
        mRecyclerView = findViewById(R.id.recyclerView);

        //bepaald hoeveel items naast elkaar worden laten zien op basis van welke rotatie het scherm heeft
        int gridColumnCount = 1;
        //set layout manager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
        Log.i(TAG, "grid column count: " + gridColumnCount);
        mRecyclerView.setAdapter(mAdapter);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
            Log.w(TAG, "No internet connection");
        }

        if (networkInfo != null && networkInfo.isConnected()) {
            mMovie.execute();
            Log.i(TAG, "executing meal");
        }

    }


    @Override
    public void onRefresh() {
        Toast.makeText(this,
                "kan nog niet reloaden",
                Toast.LENGTH_SHORT)
                .show();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}