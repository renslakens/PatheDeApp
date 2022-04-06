package com.groep3.pathedeapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.List;
import com.groep3.pathedeapp.domain.LoadedLists;
import com.groep3.pathedeapp.domain.User;

import java.io.IOException;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    private final LinkedList<List> mListList = new LinkedList<>();
    private ListList mAdapter;
    private RecyclerView mRecyclerView;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private Integer userId = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getAccount();
        getLists();
    }

    //Nieuwe lijst aanmaken met onClick
    public void createNewList(View view) {
        Intent intent = new Intent(this, ListCreateActivity.class);
        startActivity(intent);
    }

    private void setAdapter() {
        mRecyclerView = findViewById(R.id.listRecyclerView);
        mAdapter = new ListList(this, mListList, userId, getIntent().getStringExtra("session_id"), getIntent().getBooleanExtra("logged_in", true));
        mRecyclerView.setAdapter(mAdapter);
        int gridColumnCount = 1;

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
    }

    private void getAccount() {
        Call<User> userCall = apiInterface.getAccount("11db3143a380ada0de96fe9028cbc905", getIntent().getStringExtra("session_id"));

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Log.d("test", String.valueOf(user.getId()));
                userId = user.getId();
//                try {
//                    Log.d("Error occurred", "failure " + response.headers() + response.errorBody().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                mListList.addAll(lists.getLists());
//                setAdapter();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    public void getLists() {

        Call<LoadedLists> listCall = apiInterface.getLists(userId, "11db3143a380ada0de96fe9028cbc905", getIntent().getStringExtra("session_id"));

        listCall.enqueue(new Callback<LoadedLists>() {

            @Override
            public void onResponse(Call<LoadedLists> call, Response<LoadedLists> response) {
                LoadedLists lists = response.body();
                Log.d("test", response.body().toString());
//                try {
//                    Log.d("Error occurred", "failure " + response.headers() + response.errorBody().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                mListList.addAll(lists.getLists());
                setAdapter();
            }

            @Override
            public void onFailure(Call<LoadedLists> call, Throwable t) {
//                Log.d("test", String.valueOf(t));
            }
        });

    }
}