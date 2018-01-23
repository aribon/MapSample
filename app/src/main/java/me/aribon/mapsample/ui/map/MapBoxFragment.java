package me.aribon.mapsample.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import me.aribon.mapsample.R;
import me.aribon.mapsample.ui.base.BaseMapFragment;

/**
 * @Author: aribon
 * @Date: 22/01/2018
 */

public abstract class MapBoxFragment extends BaseMapFragment<MapboxMap>
    implements MapContract.View, MapboxMap.OnCameraMoveListener, MapboxMap.OnCameraIdleListener,
    MapboxMap.OnCameraMoveStartedListener, MapboxMap.OnCameraMoveCanceledListener {

  private MapView mapView = null;

  @Override
  public int getLayoutResource() {
    return R.layout.fragment_map;
  }

  public void setMapView(MapView mapView) {
    this.mapView = mapView;
  }

  @Override
  public double getMapLatitude() {
    return getMap().getCameraPosition().target.getLatitude();
  }

  @Override
  public double getMapLongitude() {
    return getMap().getCameraPosition().target.getLongitude();
  }

  @Override
  public void initializeMap() {
    mapView.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(MapboxMap mapboxMap) {
        mapboxMap.addOnCameraMoveListener(MapBoxFragment.this);
        mapboxMap.addOnCameraIdleListener(MapBoxFragment.this);
        mapboxMap.addOnCameraMoveStartedListener(MapBoxFragment.this);
        mapboxMap.addOnCameraMoveCancelListener(MapBoxFragment.this);

        setMap(mapboxMap);

        onReady();
      }
    });
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //Start initialization of map
    if (mapView == null) {
      throw new IllegalStateException("MapView need to be initialize");
    }

    mapView.onCreate(savedInstanceState);

    initializeMap();
  }

  @Override
  public void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  public void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override
  public void moveMap(double lat, double lng, double zoom) {
    getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));
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
