package com.example.android.myweather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import  android.location.Location;
import  android.location.LocationManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class MainActivity extends AppCompatActivity {

    ProgressBar loader;
    String city = "Vellore,India";

    public static TextView  temp,location,country,weather;

    String OPEN_WEATHER_MAP_API = "2fc36db5b841573de02982d9d3b8970a";
    Double latitude = 0.0,longitude =0.0;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        setContentView(R.layout.activity_main);

        location = findViewById(R.id.location);
        temp = findViewById(R.id.temp);
        country = findViewById(R.id.country);
        weather = findViewById(R.id.weather);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Cannot access location",Toast.LENGTH_SHORT).show();
            return;
        }        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        latitude = (location.getLatitude());
                        longitude = (location.getLongitude());
                        DownloadWeather downloadWeather = new DownloadWeather();
                        downloadWeather.execute("https://api.openweathermap.org/data/2.5/weather?lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude)
                                +"&appid="+OPEN_WEATHER_MAP_API);

                    }
                });




    }
}
