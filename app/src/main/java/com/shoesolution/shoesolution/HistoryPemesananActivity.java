package com.shoesolution.shoesolution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class HistoryPemesananActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_pemesanan);

        toolbar = findViewById(R.id.toolbar_history_pemesanan);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("#9234248");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
