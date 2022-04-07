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
    private TextView mListName;
    private TextView mListDescription;
    private final String TAG = MainActivity.class.getSimpleName();
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_create);

        mListName.findViewById(R.id.nameList);
        mListDescription.findViewById(R.id.descriptionList);
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
                Log.d(TAG, String.valueOf(list.getId()));
            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {

            }
        });
    }

}