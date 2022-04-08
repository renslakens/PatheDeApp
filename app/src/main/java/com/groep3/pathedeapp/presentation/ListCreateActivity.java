package com.groep3.pathedeapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Button buttonListCreate;
    private final String TAG = MainActivity.class.getSimpleName();
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_create);

        mListName.findViewById(R.id.nameList);
        mListDescription.findViewById(R.id.descriptionList);
        buttonListCreate.findViewById(R.id.buttonListCreate);

        buttonListCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mListName.getText().toString();
                String description = mListDescription.getText().toString();

                Call<List> listCall = apiInterface.createList("11db3143a380ada0de96fe9028cbc905", getIntent().getStringExtra("session_id"), name, description);
                listCall.enqueue(new Callback<List>() {
                    @Override
                    public void onResponse(Call<List> call, Response<List> response) {
                        List list = response.body();
                        Log.d(TAG, String.valueOf(list.getId()));

                    }

                    @Override
                    public void onFailure(Call<List> call, Throwable t) {
                        Log.e(TAG, "Cannot create new list");
                    }
                });

            }

        });
    }

    public void createList(View view) {
        //Moet list in recyclerview zetten en daarna doorsturen naar ListActivity class
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

}