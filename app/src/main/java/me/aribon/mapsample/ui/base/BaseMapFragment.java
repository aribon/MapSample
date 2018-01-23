package me.aribon.mapsample.ui.base;

/**
 * @Author: aribon
 * @Date: 22/01/2018
 */

public abstract class BaseMapFragment<M> extends BaseFragment {

  private M map;

  public M getMap() {
    return map;
  }

  public void setMap(M map) {
    this.map = map;
  }

  public abstract double getMapLatitude();

  public abstract double getMapLongitude();

  public abstract void initializeMap();

  public abstract void onReady();

  public abstract void moveMap(double lat, double lng, double zoom);

  public void onMapMove() {

  }

  public void onMapIdle() {

  }

  public void onMapMoveStarted() {

  }

  public void onMapMoveCanceled() {

  }
}
