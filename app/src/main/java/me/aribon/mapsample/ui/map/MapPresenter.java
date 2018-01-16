package me.aribon.mapsample.ui.map;

import android.location.Location;

import com.google.android.gms.tasks.OnSuccessListener;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;

import me.aribon.mapsample.backend.LocationManager;
import me.aribon.mapsample.ui.base.BaseActivity;
import me.aribon.mapsample.ui.base.BasePresenter;
import me.aribon.mapsample.utils.constant.MapConstant;

/**
 * @Author: aribon
 * @Date: 16/01/2018
 */

public class MapPresenter extends BasePresenter implements MapContract.Presenter {

    private BaseActivity activity;
    private MapContract.View mvpView;

    public MapPresenter(BaseActivity activity, MapContract.View mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void subscribe() {
        super.subscribe();
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        mvpView = null;
    }

    @Override
    public void centerMap() {
        fetchCurrentPosition();
    }

    private void fetchCurrentPosition() {
        new LocationManager()
                .init()
                .fetchCurrentPosition(
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                moveMapTo(location);
                            }
                        }
                );
    }

    private void moveMapTo(Location location) {
        if(location != null) {
            mvpView.moveTo(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), MapConstant.DEFAULT_ZOOM));
        } else {
            mvpView.moveTo(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(MapConstant.NOTRE_DAME_PARIS_LAT, MapConstant.NOTRE_DAME_PARIS_LON), MapConstant.DEFAULT_ZOOM));
        }
    }
}
