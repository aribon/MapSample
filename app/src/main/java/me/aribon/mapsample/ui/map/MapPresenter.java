package me.aribon.mapsample.ui.map;

import android.location.Location;

import com.google.android.gms.tasks.OnSuccessListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.aribon.mapsample.backend.manager.LocationManager;
import me.aribon.mapsample.business.bus.AddressBus;
import me.aribon.mapsample.business.bus.LocationBus;
import me.aribon.mapsample.business.event.AddressEvent;
import me.aribon.mapsample.business.event.LocationEvent;
import me.aribon.mapsample.ui.base.BasePresenter;
import me.aribon.mapsample.utils.LocationUtils;
import me.aribon.mapsample.utils.constant.MapConstant;

/**
 * @Author: aribon
 * @Date: 16/01/2018
 */

public class MapPresenter extends BasePresenter implements MapContract.Presenter {

  private MapContract.View mvpView;

  private Disposable addressBus;

  public MapPresenter(MapContract.View mvpView) {
    this.mvpView = mvpView;
  }

  @Override
  public void subscribe() {
    super.subscribe();

    addressBus = AddressBus.getInstance().register()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            new Consumer<AddressEvent>() {
              @Override
              public void accept(AddressEvent addressEvent) throws Exception {
                if (addressEvent != null && addressEvent.hasContent()) {
                  if (addressEvent.equalsType(AddressEvent.AddressEventType.FROM_INPUT)) {
                    mvpView.moveTo(addressEvent.getEventContent().getLatitude(),
                        addressEvent.getEventContent().getLongitude(),
                        MapConstant.DEFAULT_ZOOM);
                  }
                }
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                throwable.getMessage();
              }
            }
        );
  }

  @Override
  public void unsubscribe() {
    if (addressBus != null && !addressBus.isDisposed()) {
      addressBus.dispose();
    }

    addressBus = null;
    mvpView = null;
    super.unsubscribe();
  }

  /**
   * Search current location of the user
   * Then center the map with the position found
   */
  @Override
  public void centerMap() {
    findCurrentPosition();
  }

  @Override
  public void mapMoved(double lat, double lng) {
    sendPosition(lat, lng);

  }

  private void findCurrentPosition() {
    new LocationManager()
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

  private void sendPosition(double lat, double lng) {
    LocationBus.getInstance()
        .send(new LocationEvent(
            LocationUtils.transposeToLatLng(lat, lng),
            LocationEvent.LocationEventType.FROM_MAP));
  }
}
