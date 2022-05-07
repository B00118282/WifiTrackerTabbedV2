package com.example.wifi_tracker_tabbed.ui.main;

import static java.lang.String.valueOf;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wifi_tracker_tabbed.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationGrab extends Fragment {

    public class LocationApp extends AppCompatActivity {
        private int FAST_UPDATE_INTERVAL() {
            return 5;
        }

        private int DEFAULT_UPDATE_INTERVAL() {
            return 30;
        }

        private static final int PERMISSIONS_FINE_LOCATION = 10;
        TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed, tv_sensor, tv_updates, tv_address;

        Switch sw_locationupdates, sw_gps;

        // Config file for Location Request
        LocationRequest locationRequest;

        // Google's GPS API
        FusedLocationProviderClient fusedLocationProviderClient;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_locationgrab);

            // value for each UI variable

            tv_lat = findViewById(R.id.tv_lat);
            tv_lon = findViewById(R.id.tv_lon);
            tv_altitude = findViewById(R.id.tv_altitude);
            tv_accuracy = findViewById(R.id.tv_accuracy);
            tv_speed = findViewById(R.id.tv_speed);
            tv_sensor = findViewById(R.id.tv_sensor);
            tv_updates = findViewById(R.id.tv_updates);
            tv_address = findViewById(R.id.tv_address);
            sw_locationupdates = findViewById(R.id.sw_locationupdates);
            sw_gps = findViewById(R.id.sw_gps);


            // set LocationRequest properties AKA how often it updates

            locationRequest = LocationRequest.create()
                    .setInterval(1000 * DEFAULT_UPDATE_INTERVAL())
                    .setFastestInterval(1000 * FAST_UPDATE_INTERVAL())
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            sw_gps.setOnClickListener(v -> {
                if (sw_gps.isChecked()) {
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    tv_sensor.setText("Using GPS sensors");
                } else {
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_sensor.setText("Using Towers + WIFI");
                }

            });




            updateGPS();

        } //end of onCreate method

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);


            switch (requestCode) {
                case PERMISSIONS_FINE_LOCATION:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        updateGPS();
                    } else {
                        Toast.makeText(this, "This app requires permissions", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;

            }

        }

        public void updateGPS() {

            //get permissions from the user to get their Location
            //get current location from fused client
            //update UI AKA textviews

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            if (androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {

                //ask user for permission
                //we got permissions! now display the values
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        updateUIValues(location);
                    }
                });
            } else {
                //permission not granted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);

                }
            }
        }
        public void updateUIValues (android.location.Location location){

            //update textviews with new location
            tv_lat.setText(String.valueOf(location.getLatitude()));
            tv_lon.setText(String.valueOf(location.getLongitude()));
            tv_accuracy.setText(String.valueOf(location.getAccuracy()));

            if (location.hasAltitude()) {
                tv_altitude.setText(String.valueOf(location.getAltitude()));
            }
            else {
                tv_altitude.setText("Not available");
            }
            if (location.hasSpeed()) {
                tv_speed.setText(String.valueOf(location.getSpeed()));
            }
            else {
                tv_speed.setText("Not available");
            }

        }
    }
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locationgrab, container, false);
}
}

