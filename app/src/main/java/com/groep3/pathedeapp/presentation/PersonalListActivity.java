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

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalListActivity extends AppCompatActivity {
    private final LinkedList<List> mListList = new LinkedList<>();
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
    }

    //Nieuwe lijst aanmaken met onClick
    public void createNewList(View view) {
        Intent intent = new Intent(this, ListCreateActivity.class);
        startActivity(intent);
    }

    private void setAdapter() {
        mRecyclerView = findViewById(R.id.listRecyclerView);
        mAdapter = new PersonalListList(this, mListList);
        mRecyclerView.setAdapter(mAdapter);
        int gridColumnCount = 1;

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
    }


    public void getList() {

        Call<LoadedLists> listCall = apiInterface.getList(getIntent().getIntExtra("list_id", 5), "11db3143a380ada0de96fe9028cbc905");
        listCall.enqueue(new Callback<LoadedLists>() {
            @Override
            public void onResponse(Call<LoadedLists> call, Response<LoadedLists> response) {
                Log.d("test", response.body().toString());
                Log.d("Error occurred", "failure " + response.headers() + response.errorBody().toString());
                LoadedLists lists = response.body();
                Log.d("test", String.valueOf(lists.getLists().get(1)));
//                mListList.addAll(lists.getLists());
//                setAdapter();
            }

            @Override
            public void onFailure(Call<LoadedLists> call, Throwable t) {
                Log.d("error", "error");
            }
        });

    }
}
