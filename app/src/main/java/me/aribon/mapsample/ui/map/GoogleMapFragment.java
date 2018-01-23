package me.aribon.mapsample.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import me.aribon.mapsample.R;
import me.aribon.mapsample.ui.base.BaseMapFragment;

/**
 * @Author: aribon
 * @Date: 22/01/2018
 */

public abstract class GoogleMapFragment extends BaseMapFragment<GoogleMap>
    implements MapContract.View, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener,
    GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveCanceledListener {

  private SupportMapFragment mapFragment  = null;

  @Override
  public int getLayoutResource() {
    return R.layout.fragment_map;
  }

  public void setMapFragment(SupportMapFragment mapView) {
    this.mapFragment = mapView;
  }

  @Override
  public double getMapLatitude() {
    return getMap().getCameraPosition().target.latitude;
  }

  @Override
  public double getMapLongitude() {
    return getMap().getCameraPosition().target.longitude;
  }

  @Override
  public void initializeMap() {
    mapFragment .getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnCameraMoveListener(GoogleMapFragment.this);
        googleMap.setOnCameraIdleListener(GoogleMapFragment.this);
        googleMap.setOnCameraMoveStartedListener(GoogleMapFragment.this);
        googleMap.setOnCameraMoveCanceledListener(GoogleMapFragment.this);

        setMap(googleMap);

        onReady();
      }
    });
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //Start initialization of map
    if (mapFragment  == null) {
      throw new IllegalStateException("MapFragment need to be pass to setMapFragment()");
    }

    initializeMap();
  }

  @Override
  public void moveMap(double lat, double lng, double zoom) {
    getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), (float) zoom));
  }

  @Override
  public final void onCameraMove() {
    onMapMove();
  }

  @Override
  public final void onCameraIdle() {
    onMapIdle();
  }

  @Override
  public final void onCameraMoveStarted(int reason) {
    onMapMoveStarted();
  }

  @Override
  public final void onCameraMoveCanceled() {
    onMapMoveCanceled();
  }
}
