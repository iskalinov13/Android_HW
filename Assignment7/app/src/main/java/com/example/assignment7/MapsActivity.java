package com.example.assignment7;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.AppComponentFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper databaseHelper;
    private Dialog dialog;
    private ArrayList<Place> places;
    private MyAdapter adapter;
    private ListView places_list;
    private EditText location_name,location_description;
    private String str_latitude,str_longitude;
    private Animation scale_up,fade_out;



    private RelativeLayout first_layout;
    private LinearLayout second_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        databaseHelper = new DatabaseHelper(this);
        places_list= findViewById(R.id.listView);
        places = databaseHelper.getPlaces();
        adapter = new MyAdapter(this,places);
        places_list.setAdapter(adapter);

        places_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeLayout();
                moveTo(places.get(position).getLatitude(),places.get(position).getLongitude());
            }
        });

        first_layout = (RelativeLayout)findViewById(R.id.first_layout);
        second_layout = (LinearLayout) findViewById(R.id.second_layout);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_view);

        location_name = (EditText)dialog.findViewById(R.id.et_lname);
        location_description = (EditText)dialog.findViewById(R.id.et_ldesc);

        scale_up= AnimationUtils.loadAnimation(this, R.anim.scaleup);
        fade_out= AnimationUtils.loadAnimation(this, R.anim.fade_out);

        first_layout = (RelativeLayout)findViewById(R.id.first_layout);
        second_layout = (LinearLayout)findViewById(R.id.second_layout);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                changeLayout();
                break;
        }
        return true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        System.out.println(mMap.getCameraPosition().toString());
        // Add a marker in Sydney and move the camera
        openMap();

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                dialog.show();
                str_latitude = latLng.latitude+"";
                str_longitude = latLng.longitude+"";
            }
        });

        dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place place =  new Place(location_name.getText().toString(),location_description.getText().toString(),str_latitude,str_longitude);
                databaseHelper.insertPlace(place);
                adapter.notifyDataSetChanged();
                dialog.cancel();
                onResume();
                moveTo(str_latitude,str_longitude);
            }
        });

        dialog.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                onResume();
            }
        });
    }

    public void radioClicked(View v){
        switch (v.getId()){
            case R.id.standard:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

        }
    }

    public void moveTo(String str_latitude, String str_longitude){
        LatLng latLng = new LatLng(Double.parseDouble(str_latitude),Double.parseDouble(str_longitude));
        mMap.addMarker(new MarkerOptions().position(latLng).title(location_name.getText().toString()).snippet(location_description.getText().toString()));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng),1000,null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        places = databaseHelper.getPlaces();
        adapter = new MyAdapter(this,places);
        places_list.setAdapter(adapter);
    }

    public void openMap(){
        for(int i=0; i<places.size(); i++){
            LatLng latLng = new LatLng(Double.parseDouble(places.get(i).getLatitude()),Double.parseDouble(places.get(i).getLongitude()));
            mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i).getLocation_name()).snippet(places.get(i).getLocation_description()));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng),1000,null);
        }


    }

    public void changeLayout(){
        if(first_layout.getVisibility()==View.GONE){
            first_layout.startAnimation(scale_up);
            first_layout.setVisibility(View.VISIBLE);
            second_layout.setVisibility(View.GONE);
        }
        else{
            first_layout.setVisibility(View.GONE);
            second_layout.startAnimation(fade_out);
            second_layout.setVisibility(View.VISIBLE);
        }
    }




}
