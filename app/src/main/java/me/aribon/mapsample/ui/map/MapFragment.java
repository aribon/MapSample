package me.aribon.mapsample.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;

import me.aribon.mapsample.R;
import me.aribon.mapsample.utils.ResUtils;
import me.aribon.mapsample.utils.constant.RequestConstant;

/**
 * @Author: aribon
 * @Date: 16/01/2018
 */

public class MapFragment extends GoogleMapFragment {

  public static MapFragment newInstance() {

    Bundle args = new Bundle();

    MapFragment fragment = new MapFragment();
    fragment.setArguments(args);
    return fragment;
  }

  private MapContract.Presenter presenter;

  private FloatingActionButton btnMyLocation;

  @Override
  public int getLayoutResource() {
    return R.layout.fragment_map;
  }

  @Override
  public void findView(View view) {
    super.findView(view);
    //If MapBox
//    setMapView(view.findViewById(R.id.mapView));

    //If GoogleMap
    setMapFragment((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView));

    btnMyLocation = view.findViewById(R.id.map_my_location_button);
  }

  @Override
  public void initializePresenter() {
    super.initializePresenter();
    //Initialization of the presenter
    presenter = new MapPresenter(this);
  }

  @Override
  public void initializeData() {
    super.initializeData();
    presenter.subscribe();
  }

  @Override
  public void initializeView() {
    super.initializeView();
    //Initialize fab button click
    btnMyLocation.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        presenter.centerMap();
      }
    });
  }

  /**
   * Move map to the determined coordinate
   *
   * @param lat  latitude
   * @param lng  longitude
   * @param zoom zoom value
   */
  @Override
  public void moveTo(double lat, double lng, double zoom) {
    moveMap(lat, lng, zoom);
  }

  @Override
  public void onReady() {
    presenter.subscribe();
    if (checkPermissions())
      presenter.centerMap();
  }

  @Override
  public void onDestroy() {
    presenter.unsubscribe();
    super.onDestroy();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[],
                                         int[] grantResults) {
    switch (requestCode) {
      case RequestConstant.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          presenter.centerMap();
        } else {
          showMessage(ResUtils.getString(R.string.error_permission_access_fine_location));
        }
        return;
      }

      case RequestConstant.MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          presenter.centerMap();
        } else {
          showMessage(ResUtils.getString(R.string.error_permission_access_coarse_location));
        }
        return;
      }
    }
  }

  public boolean checkPermissions() {
    if (ContextCompat.checkSelfPermission(getContext(),
        Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(getContext(),
        Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
          
          if (shouldShowRequestPermissionRationale(
                  Manifest.permission.ACCESS_FINE_LOCATION)) {
              showMessage(ResUtils.getString(R.string.error_permission_access_fine_location));
          } else {
              requestPermissions(
                      new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                      RequestConstant.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

              return false;
          }

          if (shouldShowRequestPermissionRationale(
                  Manifest.permission.ACCESS_COARSE_LOCATION)) {
              showMessage(ResUtils.getString(R.string.error_permission_access_coarse_location));
          } else {
              requestPermissions(
                      new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                      RequestConstant.MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

              return false;
          }
        }

    return true;
  }

  @Override
  public void onMapIdle() {
    presenter.mapMoved(getMapLatitude(), getMapLongitude());
  }
}
