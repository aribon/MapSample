package me.aribon.mapsample.ui.map;

import com.mapbox.mapboxsdk.camera.CameraUpdate;

import me.aribon.mapsample.ui.base.BaseMvpPresenter;
import me.aribon.mapsample.ui.base.BaseMvpView;

/**
 * @Author: aribon
 * @Date: 15/01/2018
 */

public interface MapContract {

    interface View extends BaseMvpView {

        void moveTo(CameraUpdate cameraUpdate);
    }

    interface Presenter extends BaseMvpPresenter {

        void centerMap();
    }
}
