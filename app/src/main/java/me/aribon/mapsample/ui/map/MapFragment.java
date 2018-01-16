package me.aribon.mapsample.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import me.aribon.mapsample.R;
import me.aribon.mapsample.ui.base.BaseFragment;
import me.aribon.mapsample.utils.constant.RequestConstant;

/**
 * @Author: aribon
 * @Date: 16/01/2018
 */

public class MapFragment extends BaseFragment implements MapContract.View {

    public static MapFragment newInstance() {

        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private MapContract.Presenter presenter;

    private MapView mapView;
    private MapboxMap mapboxMap;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                MapFragment.this.mapboxMap = mapboxMap;
                if (checkPermissions())
                    presenter.centerMap();
            }
        });
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
        presenter.unsubscribe();
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_mapbox;
    }

    @Override
    public void findView(View view) {
        super.findView(view);
        mapView = (MapView) view.findViewById(R.id.mapView);
    }

    @Override
    public void initializePresenter() {
        super.initializePresenter();
        presenter = new MapPresenter(getParentActivity(), this);
    }

    @Override
    public void moveTo(CameraUpdate cameraUpdate) {
        mapboxMap.moveCamera(cameraUpdate);
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
                    showMessage("Veuillez activer la permission ACCESS_FINE_LOCATION");
                }
                return;
            }

            case RequestConstant.MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.centerMap();
                } else {
                    showMessage("Veuillez activer la permission ACCESS_COARSE_LOCATION");
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
                showMessage("Veuillez activer la permission ACCESS_FINE_LOCATION");
            } else {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        RequestConstant.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                showMessage("Veuillez activer la permission ACCESS_COARSE_LOCATION");
            } else {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        RequestConstant.MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            }

            return false;
        }

        return true;
    }
}
