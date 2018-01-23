package me.aribon.mapsample.backend;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import me.aribon.mapsample.AppApplication;

/**
 * @Author: aribon
 * @Date: 16/01/2018
 */

public class LocationManager {

    private FusedLocationProviderClient mFusedLocationClient;

    public LocationManager() {

    }

    public LocationManager init() {
        mFusedLocationClient = LocationServices
                .getFusedLocationProviderClient(AppApplication.getInstance().getApplicationContext());

        return this;
    }

    public void fetchCurrentPosition(OnSuccessListener<Location> successListener) {
        if (ActivityCompat.checkSelfPermission(AppApplication.getInstance().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(AppApplication.getInstance().getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(successListener);
    }

}
