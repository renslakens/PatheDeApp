package com.groep3.pathedeapp.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.groep3.pathedeapp.R;

public class ListCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_create);
    }

    public void createList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}