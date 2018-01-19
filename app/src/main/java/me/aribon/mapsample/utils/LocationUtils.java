package me.aribon.mapsample.utils;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by anthony.ribon
 * On 19/01/2018
 */

public class LocationUtils {

  public static LatLng transposeToLatLng(double lat, double lng) {
    return new LatLng(lat, lng);
  }

  public static LatLng transposeToLatLng(Location location) {
    return location != null
        ? new LatLng(location.getLatitude(), location.getLongitude())
        : new LatLng(0, 0);
  }

  public static double transposeToLatitude(LatLng latLng) {
    return latLng != null ? latLng.latitude : 0;
  }

  public static double transposeToLongitude(LatLng latLng) {
    return latLng != null ? latLng.longitude : 0;
  }

}
