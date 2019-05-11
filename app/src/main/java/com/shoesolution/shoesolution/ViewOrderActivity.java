package com.shoesolution.shoesolution;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewOrderActivity extends FragmentActivity implements OnMapReadyCallback {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView text_customer, text_service, text_perfume, text_quantity, text_total, text_judul;
//    private MapView mapView;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        //INITIATE VARIABLE
        text_customer = findViewById(R.id.textView75);
        text_service = findViewById(R.id.textView77);
        text_perfume = findViewById(R.id.textView79);
        text_quantity = findViewById(R.id.textView81);
        text_total = findViewById(R.id.textView83);
        text_judul = findViewById(R.id.textView53);
//        mapView = findViewById(R.id.mapView2);

        //MAPS
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        final String code = intent.getStringExtra("uniquecode");

        db.collection("order").whereEqualTo("uniquecode", code).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document : task.getResult()){
                                text_customer.setText(document.getString("customer"));
                                text_service.setText(document.getString("service"));
                                text_perfume.setText(document.getString("perfume"));
                                text_quantity.setText(document.getString("quantity"));
                                text_total.setText(document.getString("harga"));
                                text_judul.setText("#"+code+" / " + document.getString("status"));
                            }
                        }
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng outlet = new LatLng(-6.974028, 107.630531);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(outlet, 15);
        mMap.addMarker(new MarkerOptions().position(outlet).title("Shoesolution Outlet"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(outlet));
        mMap.animateCamera(location);
    }
}
