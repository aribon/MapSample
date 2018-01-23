package me.aribon.mapsample.ui.map;

import me.aribon.mapsample.ui.base.BaseMvpPresenter;
import me.aribon.mapsample.ui.base.BaseMvpView;

/**
 * @Author: aribon
 * @Date: 15/01/2018
 */

public interface MapContract {

  interface View extends BaseMvpView {

    /**
     * @param lat
     * @param lng
     * @param zoom
     */
    void moveTo(double lat, double lng, double zoom);
  }

  interface Presenter extends BaseMvpPresenter {

    void centerMap();

    void mapMoved(double lat, double lng);
  }
}
