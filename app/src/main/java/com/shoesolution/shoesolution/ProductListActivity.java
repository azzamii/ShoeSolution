package com.shoesolution.shoesolution;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<Product, ProductHolder> adapter;
    private FirebaseFirestore rootref;
    private Query query;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        //INITIATE
        recyclerView = findViewById(R.id.recyclerViewProduct);
        toolbar = findViewById(R.id.toolbar_product);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Our Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //FIRESTORE
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rootref = FirebaseFirestore.getInstance();
        query = rootref.collection("products");
        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>().setQuery(query, Product.class).build();
        adapter = new FirestoreRecyclerAdapter<Product, ProductHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull final Product model) {
                holder.setNama(model.getNama());
                holder.setHarga(model.getHarga());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProductListActivity.this, DetailPemesananActivity.class);
                        intent.putExtra("nama", model.getNama());
                        intent.putExtra("harga", model.getHarga());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_product, viewGroup, false);
                return new ProductHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private View view;

        ProductHolder(View itemView){
            super(itemView);
            view = itemView;
        }

        void setNama(String nama){
            TextView textView = view.findViewById(R.id.textView3);
            textView.setText(nama);
        }

        void setHarga(String harga){
            TextView textView = view.findViewById(R.id.textView4);
            textView.setText(harga);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
