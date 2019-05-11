package com.shoesolution.shoesolution;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner service, perfume;
    ImageButton plus_button, mins_button;
    TextView text_jumlah, text_totalharga;
    int total = 0;
    int harga = 0;
    NumberFormat formatRupiah;
    Button order;
    EditText customer;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        toolbar = findViewById(R.id.toolbar_order);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //INITIATE atau mendeskripsikan variabel

        service = findViewById(R.id.spinner4);
        perfume = findViewById(R.id.spinner5);
        plus_button = findViewById(R.id.imageButton2);
        mins_button = findViewById(R.id.imageButton);
        order = findViewById(R.id.button12);
        customer = findViewById(R.id.editText11);

        //FORMAT RUPIAH
        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        //SERVICE, mendeskripsikan service
        String services[] = {"VIP", "Regular", "Light"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, services);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        service.setAdapter(spinnerArrayAdapter);

        //PERFUME, mendeskripsikan perfume
        String perfumes[] = {"Raspberry", "Aqua", "Blackberry", "Peach"};
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, perfumes);
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        perfume.setAdapter(spinnerArrayAdapter1);

        //JUMLAH
        mins_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total == 0) {
                    total = 0;
                    setText_jumlah(total);
                    setTotal(harga * total);
                } else {
                    total = total - 1;
                    setText_jumlah(total);
                    setTotal(harga * total);
                }
            }
        });

        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total = total + 1;
                setText_jumlah(total);
                setTotal(harga * total);
            }
        });

        //Detail Service
        service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //GET PILIHAN
                String service_pilihan = parent.getSelectedItem().toString();

                //GET HARGA BASED ON SERVICE
                if (service_pilihan.equals("VIP")) {
                    harga = 35000;
                    setTotal(harga * total);
                } else if (service_pilihan.equals("Regular")) {
                    harga = 25000;
                    setTotal(harga * total);
                } else if (service_pilihan.equals("Light")) {
                    harga = 15000;
                    setTotal(harga * total);
                } else {
                    harga = 0;
                    setTotal(harga * total);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //CHECK IF ORDER FROM CUSTOMER OR ADMIN
        final boolean isCustomer = getIntent().getBooleanExtra("customer", false);
        Log.d("mencoba", String.valueOf(isCustomer));

        //ORDER BUTTON
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomnumber = (int) Math.round((Math.random() * 5000) + 1000);
                String customername = customer.getText().toString().replace(" ", "");
                final String uniquecode = customername + "-" + String.valueOf(randomnumber);
                Log.d("mencoba", uniquecode);

                Map<String, Object> order = new HashMap<>();
                order.put("uniquecode", uniquecode);
                order.put("customer", customer.getText().toString());
                order.put("service", service.getSelectedItem().toString());
                order.put("perfume", perfume.getSelectedItem().toString());
                order.put("quantity", String.valueOf(total));
                order.put("harga", text_totalharga.getText().toString());
                order.put("status", "Received");
                order.put("payment_method","InStore");

                db.collection("order")
                        .add(order)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Intent intent = new Intent(OrderActivity.this, SummaryActivity.class);
                                intent.putExtra("unique", uniquecode);
                                intent.putExtra("customer", customer.getText().toString());
                                intent.putExtra("service", service.getSelectedItem().toString());
                                intent.putExtra("perfume", perfume.getSelectedItem().toString());
                                intent.putExtra("quantity", String.valueOf(total));
                                intent.putExtra("harga", text_totalharga.getText().toString());
                                intent.putExtra("status", "Received");
                                intent.putExtra("role", isCustomer);
                                startActivity(intent);
                            }
                        });
            }
        });

    }

    public void setText_jumlah(int jumlah) {
        text_jumlah = findViewById(R.id.textView57);
        text_jumlah.setText(String.valueOf(jumlah));
    }

    public void setTotal(int harga) {
        text_totalharga = findViewById(R.id.textView59);
        text_totalharga.setText(String.valueOf(formatRupiah.format((double) harga)));
    }
}
