package me.aribon.mapsample.utils;

import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by anthony.ribon
 * On 19/01/2018
 */

public class MapBoxUtils {

  public static LatLng transposeToLatLng(double lat, double lng) {
    return new LatLng(lat, lng);
  }

  public static double transposeToLatitude(LatLng latLng) {
    return latLng != null ? latLng.getLatitude() : 0;
  }

  public static double transposeToLongitude(LatLng latLng) {
    return latLng != null ? latLng.getLongitude() : 0;
  }
}
