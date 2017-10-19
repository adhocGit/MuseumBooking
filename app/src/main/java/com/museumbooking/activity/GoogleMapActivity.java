package com.museumbooking.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.museumbooking.R;
import com.museumbooking.conf.Constants;

public class GoogleMapActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback {
    private MapFragment mMap;// Google map variable
    private Double latitude, longitude;
    private Button viewRouteMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        setView();
    }

    private void setView() {
        try {
            if (null != getIntent()) {
                latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
                longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMap = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMap.getMapAsync(this);
        viewRouteMapButton = (Button) findViewById(R.id.viewRouteMapButton);
        viewRouteMapButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewRouteMapButton:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        int status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(GoogleMapActivity.this);
        if (status == ConnectionResult.SUCCESS) {
            googleMap.getUiSettings().setZoomControlsEnabled(false);
        }
//        LatLng mStore = new LatLng(getPointedLatLog(latitude),
//                getPointedLatLog(longitude));
//        Marker mMarker = googleMap.addMarker(new MarkerOptions().position(mStore));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mStore, 15));

        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(latitude,
                        longitude)).title("Marker"));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                        latitude, longitude),
                12.0f));


    }

    public double getPointedLatLog(double value) {
        double pointedVal = value / Constants.pointPosition;
        return pointedVal;
    }
}
