package com.groep3.pathedeapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCreateActivity extends AppCompatActivity {
    private TextView mListTitle;
    private TextView mListName;
    private TextView mListDescription;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_create);

    }

    public void createList(View view) {
        //Moet list in recyclerview zetten en daarna doorsturen naar ListActivity class
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void createList() {
        Call<List> listCall = apiInterface.createList("11db3143a380ada0de96fe9028cbc905", getIntent().getStringExtra("session_id"));

        listCall.enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {
                List list = response.body();
                Log.d("test", String.valueOf(list.getId()));
//                userId = list.getId();
//                try {
//                    Log.d("Error occurred", "failure " + response.headers() + response.errorBody().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                mListList.addAll(lists.getLists());
//                setAdapter();
            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {

            }
        });
    }

}