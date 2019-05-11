package com.shoesolution.shoesolution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class KonfirmasiActivity extends AppCompatActivity {

    Spinner spinner, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);

        //INITIATE
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner3);

        //SPINNER
        //SPINNER
        String kota[] = {"BCA","BNI","MANDIRI","BRI"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, kota);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner2.setAdapter(spinnerArrayAdapter);
    }
}
