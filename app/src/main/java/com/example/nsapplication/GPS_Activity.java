package com.example.nsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class GPS_Activity extends AppCompatActivity {

    public static final int DEFAULT_UPDATE_INTERVAL = 10;
    public static final int FAST_UPDATE_INTERVAL = 5;
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private static final int PERMISSIONS_COARSE_LOCATION = 88;


    private TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed, tv_sensor, tv_updates, tv_address, tv_countOfCrumbs;
    private Button btn_newWayPoint, btn_showMap;
    private androidx.appcompat.widget.SwitchCompat sw_locationsupdates, sw_gps;
    private ImageView btn_back;

    // variable to remember if we are tracking location or not
    boolean updateOn = false;

    Location currentLocation; // current location

    List<Location> savedLocations; // list of saved locations

    // location request is a config file for all settings related to fusedLocationProviderClient
    LocationRequest locationRequest;

    LocationCallback locationCallBack;

    // Google's API for location services. The majority of the app functions using this class.
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);


        initView();

        configLocationRequest();

        pageDirectories();

        updateGPS();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                } else {
                    Toast.makeText(this, "This app requires permission to be granted in order to work properly", Toast.LENGTH_SHORT).show();
                    finish();
                }

        }
    }

    @SuppressLint("MissingPermission")
    private void updateGPS() {
        // get permission from the user to track GPS
        // get the current location from the fused client
        // update the UI - i.e. set all properties in their associated text view items.

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(GPS_Activity.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // user provided the permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // We got permission. Put the values of location.. XXX into the UI component.
                    updateUIValues(location);
                    currentLocation = location;
                }
            });
        } else {
            // permission not granted yet.

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_COARSE_LOCATION);
            }
        }

    }

    private void updateUIValues(Location location) {

        // Update all of the text view objects with a new location.
//        tv_lat.setText(String.valueOf(location.getLatitude()));
//        tv_lon.setText(String.valueOf(location.getLongitude()));
//        tv_accuracy.setText(String.valueOf(location.getAccuracy()));
//
//        if (location.hasAltitude())
//            tv_altitude.setText(String.valueOf(location.getAltitude()));
//        else
//            tv_altitude.setText("Not available");
//
//        if (location.hasSpeed())
//            tv_speed.setText(String.valueOf(location.getSpeed()));
//        else
//            tv_speed.setText("not available");

        Geocoder geocoder = new Geocoder(GPS_Activity.this);

        try
        {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
            tv_address.setText(addressList.get(0).getAddressLine(0));
        }
        catch (Exception e)
        {
            tv_address.setText("Unable to get street address");
        }

        MyApplication myApplication = (MyApplication)getApplicationContext();
        savedLocations = myApplication.getMyLocation();

        // show the number of waypoints saved.
        tv_countOfCrumbs.setText(Integer.toString(savedLocations.size()));

    }

    private void pageDirectories() {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Main_MenuPage_Activity.class));
            }
        });

        /** OnClickListener for  GPS switch **/
        sw_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_gps.isChecked()) {
                    // most accurate - use GPS
                    locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
                    tv_sensor.setText("Using GPS sensors");
                } else {
                    locationRequest.setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_sensor.setText("Using Towers + WIFI");
                }
            }
        });


        /** OnClickListener for  location update switch **/
        sw_locationsupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_locationsupdates.isChecked())
                    // turn on location tracking
                    startLocalUpdates();
                else
                    // turn off tracking
                    stopLocalUpdates();

            }
        });

        /** OnClickListener for  New WayPoint **/
        btn_newWayPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the gps location

                // add the new location to the global list
                MyApplication myApplication = (MyApplication) getApplicationContext();
                savedLocations = myApplication.getMyLocation();
                savedLocations.add(currentLocation);
                Toast.makeText(myApplication, "Please wait for around 10 seconds for the waypoint to be updated.", Toast.LENGTH_LONG).show();
            }
        });


        /** OnClickListener for btn_showMap **/
        btn_showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GPS_Activity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void stopLocalUpdates() {
        tv_updates.setText("Location is NOT being tracked");
        tv_lat.setText("Not tracking location");
        tv_lon.setText("Not tracking location");
        tv_speed.setText("not tracking location");
        tv_address.setText("Not tracking location");
        tv_altitude.setText("Not tracking location");
        tv_sensor.setText("Not tracking location");

        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }

    @SuppressLint("MissingPermission")
    private void startLocalUpdates() {
        tv_updates.setText("Location is being tracked");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        updateGPS();

    }

    private void configLocationRequest() {
        locationRequest = LocationRequest.create(); // set all properties of LocationRequest

        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL); // how often does the the default location check occur

        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL); // how often does the location check occur when set to the most frequent update?

        locationRequest.setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);

        locationCallBack = new LocationCallback() { // event that is triggered whenever the update interval is met.
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                // save the location

                updateUIValues(locationResult.getLastLocation());
            }
        };


    }

    private void initView() {
        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_speed = findViewById(R.id.tv_speed);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_address);
        tv_countOfCrumbs = findViewById(R.id.tv_countOfCrumbs);

        btn_newWayPoint = findViewById(R.id.btn_newWayPoint);
        btn_showMap = findViewById(R.id.btn_showMap);
        btn_back = findViewById(R.id.btn_back);

        sw_locationsupdates = findViewById(R.id.sw_locationsupdates);
        sw_gps = findViewById(R.id.sw_gps);
    }
}