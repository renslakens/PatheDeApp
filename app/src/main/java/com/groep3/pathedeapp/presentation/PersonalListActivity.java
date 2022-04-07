package com.groep3.pathedeapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.List;
import com.groep3.pathedeapp.domain.LoadedLists;
import com.groep3.pathedeapp.domain.Movie;
import com.groep3.pathedeapp.domain.User;

import java.io.IOException;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalListActivity extends AppCompatActivity {
    private final LinkedList<Movie> mListList = new LinkedList<>();
    private PersonalListList mAdapter;
    private RecyclerView mRecyclerView;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private Integer userId = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_list);
//        getAccount();
        userId = getIntent().getIntExtra("userId", 5);
        getList();
        Button addToList = findViewById(R.id.addToListButton);
        addToList.setVisibility(mRecyclerView.INVISIBLE);
    }

    //Nieuwe lijst aanmaken met onClick
    public void createNewList(View view) {
        Intent intent = new Intent(this, ListCreateActivity.class);
        startActivity(intent);
    }

    private void setAdapter() {
        mRecyclerView = findViewById(R.id.personalListRecyclerview);
        mAdapter = new PersonalListList(this, mListList, getIntent().getStringExtra("session_id"), getIntent().getBooleanExtra("logged_in", true));
        mRecyclerView.setAdapter(mAdapter);
        int gridColumnCount = 1;

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
    }


    public void getList() {
        TextView listName = findViewById(R.id.listName);
        ImageButton shareButton = findViewById(R.id.detail_share_button);

        Call<com.groep3.pathedeapp.domain.List> listCall = apiInterface.getList(getIntent().getIntExtra("list_id", 5), "11db3143a380ada0de96fe9028cbc905");
        listCall.enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {
                //                Log.d("test", response.body().toString());

                    Log.d("Error occurred", "failure " + response.headers() + response.raw());

                List lists = response.body();

                mListList.addAll(lists.getItems());
                listName.setText(lists.getName());
                shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                        intent.putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/list/" + getIntent().getIntExtra("list_id", 5));
                        startActivity(Intent.createChooser(intent, "Share URL"));
                    }
                });
                setAdapter();
            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {

            }
        });

    }
}
