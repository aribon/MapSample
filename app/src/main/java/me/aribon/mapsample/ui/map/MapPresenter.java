package me.aribon.mapsample.ui.map;

import android.location.Location;

import com.google.android.gms.tasks.OnSuccessListener;

import me.aribon.mapsample.backend.LocationManager;
import me.aribon.mapsample.ui.base.BasePresenter;
import me.aribon.mapsample.utils.constant.MapConstant;

/**
 * @Author: aribon
 * @Date: 16/01/2018
 */

public class MapPresenter extends BasePresenter implements MapContract.Presenter {

    private MapContract.View mvpView;

    public MapPresenter(MapContract.View mvpView) {
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

    /**
     * Search current location of the user
     * Then center the map with the position found
     */
    @Override
    public void centerMap() {
        fetchCurrentPosition();
    }

    /**
     * Fetch the current position
     * Based on the last position given by the device
     */
    private void fetchCurrentPosition() {
        //
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

    /**
     * Order view to move map to given location
     *
     * @param location the position where to center the map
     */
    private void moveMapTo(Location location) {
        if (location != null) {
            mvpView.moveTo(
                    location.getLatitude(),
                    location.getLongitude(),
                    MapConstant.DEFAULT_ZOOM);
        } else {
            mvpView.moveTo(
                    MapConstant.NOTRE_DAME_PARIS_LAT,
                    MapConstant.NOTRE_DAME_PARIS_LON,
                    MapConstant.DEFAULT_ZOOM);
        }
    }
}
