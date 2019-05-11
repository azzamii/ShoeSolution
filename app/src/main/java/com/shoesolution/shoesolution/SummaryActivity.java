package com.shoesolution.shoesolution;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class SummaryActivity extends AppCompatActivity {

    TextView uniquecode, customer, service, perfume, quantity, total_price, text_warning;
    Spinner status;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String unique;
    String status_sebelum;
    CardView card_status, card_payment;
    Button button_chat, button_transfer, button_instore;
    boolean isCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        //INITIATE
        uniquecode = findViewById(R.id.textView61);
        customer = findViewById(R.id.textView67);
        service = findViewById(R.id.textView68);
        perfume = findViewById(R.id.textView69);
        quantity = findViewById(R.id.textView70);
        total_price = findViewById(R.id.textView71);
        status = findViewById(R.id.spinner7);
        card_status = findViewById(R.id.card_status);
        button_chat = findViewById(R.id.button11);
        button_transfer = findViewById(R.id.button14);
        button_instore = findViewById(R.id.button16);
        card_payment = findViewById(R.id.card_payment);
        text_warning = findViewById(R.id.textView85);

        //GET INTENT
        final Intent intent = getIntent();
        uniquecode.setText(intent.getStringExtra("unique"));
        customer.setText(intent.getStringExtra("customer"));
        service.setText(intent.getStringExtra("service"));
        perfume.setText(intent.getStringExtra("perfume"));
        quantity.setText(intent.getStringExtra("quantity"));
        total_price.setText(intent.getStringExtra("harga"));

        //SET GLOBAL VAR
        unique = intent.getStringExtra("unique");
        status_sebelum = intent.getStringExtra("status");
        Log.d("mencoba", status_sebelum);

        //CHECK IF ITS OPEN BY CUSTOMER
        isCustomer = intent.getBooleanExtra("role", false);
        if(isCustomer == true){
            card_status.setVisibility(View.GONE);
            button_chat.setVisibility(View.GONE);
        }else{
            card_payment.setVisibility(View.GONE);
            button_chat.setVisibility(View.GONE);
            if(intent.getStringExtra("payment_method").equals("Transfer")){
                text_warning.setText("Transfer method choosen, pickup customer's item !");
            }
        }

        //STATUS
        String statuses[] = {"Received","On Process","Ready To Pickup"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, statuses);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(spinnerArrayAdapter);

        if(status_sebelum.equals("Received")){
            status.setSelection(0);
        }else if(status_sebelum.equals("On Process") ){
            status.setSelection(1);
        }else if(status_sebelum.equals("Ready To Pickup")){
            status.setSelection(2);
        }

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pilihan = parent.getSelectedItem().toString();
                if(pilihan == "Received"){
                    updateStatus("Received");
                }else if(pilihan == "On Process"){
                    updateStatus("On Process");
                }else{
                    updateStatus("Ready To Pickup");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //CHANGE PAYMENT METHOD TO TRANSFER
        button_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, Object> data = new HashMap<>();
                data.put("payment_method", "Transfer");

                db.collection("order").whereEqualTo("uniquecode", unique).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot document : task.getResult()){
                                        db.collection("order").document(document.getId()).set(data, SetOptions.merge());
                                        Intent intent1 = new Intent(SummaryActivity.this, TransferActivity.class);
                                        intent1.putExtra("total", intent.getStringExtra("harga"));
                                        startActivity(intent1);
                                    }
                                }
                            }
                        });
            }
        });

        //CHANGE PAYMENT METHOD TO INSTORE
        button_instore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, Object> data = new HashMap<>();
                data.put("payment_method", "InStore");

                db.collection("order").whereEqualTo("uniquecode", unique).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot document : task.getResult()){
                                        db.collection("order").document(document.getId()).set(data, SetOptions.merge());
                                    }
                                }
                            }
                        });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isCustomer == true){
            Intent intent = new Intent(SummaryActivity.this, CustomerActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void updateStatus(final String statusnya){

        final Map<String, Object> data = new HashMap<>();
        data.put("status", statusnya);

        db.collection("order").whereEqualTo("uniquecode",unique).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document : task.getResult()){
                                db.collection("order").document(document.getId()).set(data, SetOptions.merge());
                            }
                        }
                    }
                });
    }
}
