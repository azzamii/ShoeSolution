package com.shoesolution.shoesolution;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class TransferActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView totalharga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        toolbar = findViewById(R.id.toolbar_transfer);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bank Transfer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //INITIATE
        totalharga = findViewById(R.id.textView22);

        Intent intent = getIntent();
        String harga_total = intent.getStringExtra("total");
        totalharga.setText(harga_total);

        //CONFIRMATION CHAT
    }
    public void chat(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=6285559642134&text=Halo%20Admin%20Saya%20Ingin%20Konfirmasi%20Pembayaran%20"));
        startActivity(intent);


    }
}
