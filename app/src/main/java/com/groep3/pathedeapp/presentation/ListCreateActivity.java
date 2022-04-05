package com.groep3.pathedeapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.groep3.pathedeapp.R;

public class ListCreateActivity extends AppCompatActivity {
    private TextView mListTitle;
    private TextView mListName;
    private TextView mListDescription;

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