package me.aribon.mapsample.business.event;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

/**
 * @Author: aribon
 * @Date: 21/01/2018
 */

public class LocationEvent extends Event<LatLng, LocationEvent.LocationEventType> {

  public enum LocationEventType {
    ALL,
    FROM_MAP;
  }

  public LocationEvent(LatLng latLng, @NonNull LocationEventType locationEventType) {
    super(latLng, locationEventType);
  }

  public LocationEvent(LatLng latLng) {
    super(latLng, LocationEventType.ALL);
  }
}
