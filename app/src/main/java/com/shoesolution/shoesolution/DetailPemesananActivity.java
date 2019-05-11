package com.shoesolution.shoesolution;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailPemesananActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView nama_produk, harga_produk;
    Button pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan);

        toolbar = findViewById(R.id.toolbar_pemesanan);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Pemesanan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //INITIATE
        nama_produk = findViewById(R.id.textView9);
        harga_produk = findViewById(R.id.textView16);
        pesan = findViewById(R.id.button5);

        Intent intent = getIntent();
        nama_produk.setText(intent.getStringExtra("nama"));
        harga_produk.setText(intent.getStringExtra("harga"));

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailPemesananActivity.this, CheckoutActivity.class);
                startActivity(intent1);
            }
        });
    }
}
