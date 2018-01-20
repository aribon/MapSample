package me.aribon.mapsample.backend;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import me.aribon.mapsample.AppApplication;

/**
 * @Author: aribon
 * @Date: 16/01/2018
 */

public class LocationManager {

  private FusedLocationProviderClient mFusedLocationClient;

  /**
   *  Find the current position of the user
   *
   * @param successListener the success callback
   */
  public void fetchCurrentPosition(OnSuccessListener<Location> successListener) {
    if (mFusedLocationClient == null) {
      mFusedLocationClient = LocationServices
          .getFusedLocationProviderClient(AppApplication.getInstance().getApplicationContext());
    }

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
