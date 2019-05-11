package com.shoesolution.shoesolution;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CustomerActivity extends AppCompatActivity {

    EditText uniquecode;
    Button button_go;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        //INITIATE VARIABLE
        button_go = findViewById(R.id.button13);
        uniquecode = findViewById(R.id.editText10);

        //GO BUTTON
        button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code = uniquecode.getText().toString();
                Log.d("mencoba",code);
                db.collection("order").whereEqualTo("uniquecode",code).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        if(document.exists()){
                                            Intent intent = new Intent(CustomerActivity.this, ViewOrderActivity.class);
                                            intent.putExtra("uniquecode", code);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Wrong Code !", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }
                        });
            }
        });
    }

    public void loginAdmin(View view) {
        Intent intent = new Intent(CustomerActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void orderCustomer(View view) {
        Intent intent = new Intent(CustomerActivity.this, OrderActivity.class);
        intent.putExtra("customer",true);
        startActivity(intent);
    }
}
