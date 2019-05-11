package com.shoesolution.shoesolution;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<Order, OrderHolder> adapter;
    private FirebaseFirestore rootref;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Dashboard");

        //INITIATE
        recyclerView = findViewById(R.id.recyclerView2);

        //FIRESTORE
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rootref = FirebaseFirestore.getInstance();
        query = rootref.collection("order");
        FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>().setQuery(query, Order.class).build();

        adapter = new FirestoreRecyclerAdapter<Order, OrderHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull OrderHolder holder, int position, @NonNull final Order model) {
                holder.setCode(model.getUniquecode(), model.getStatus());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                        intent.putExtra("unique", model.getUniquecode());
                        intent.putExtra("customer", model.getCustomer());
                        intent.putExtra("service", model.getService());
                        intent.putExtra("perfume", model.getPerfume());
                        intent.putExtra("quantity", model.getQuantity());
                        intent.putExtra("harga", model.getHarga());
                        intent.putExtra("status",model.getStatus());
                        intent.putExtra("payment_method", model.getPayment_method());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public OrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_order, viewGroup, false);
                return new OrderHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        private View view;

        OrderHolder(View itemView){
            super(itemView);
            view = itemView;
        }

        void setCode(String uniquecode, String statusnya){
            TextView textView = view.findViewById(R.id.textView51);
            textView.setText("#"+uniquecode);
            if(statusnya.equals("Ready To Pickup")){
                textView.setBackgroundColor(Color.GREEN);
            }else if(statusnya.equals("Received")){
                textView.setBackgroundColor(Color.RED);
            }
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
            else if(id == R.id.ABOUTUS){
                Intent intent = new Intent(MainActivity.this, aboutus.class);
                startActivity(intent);

        }
     else if(id == R.id.action_contactus){
            Intent intent = new Intent(MainActivity.this, contactus.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_order) {
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
