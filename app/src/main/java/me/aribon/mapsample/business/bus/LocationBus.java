package me.aribon.mapsample.business.bus;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import me.aribon.mapsample.business.event.LocationEvent;

/**
 * @Author: aribon
 * @Date: 19/01/2018
 */

public class LocationBus {

  private static volatile LocationBus instance;

  public static LocationBus getInstance() {
    if (instance == null) {
      synchronized (LocationBus.class) {
        if (instance == null) {
          instance = new LocationBus();
        }
      }
    }

    return instance;
  }

  private LocationBus() {
    if (instance != null) {
      throw new RuntimeException(
          "Use getInstance method to get the single instance of this class.");
    }
  }

  private final Relay<Object> busSubject = PublishRelay.create().toSerialized();

  public Observable<LocationEvent> register() {
    return busSubject
        .map(object -> (LocationEvent) object)
        .subscribeOn(Schedulers.io());
  }

  public void send(LocationEvent event) {
    busSubject.accept(event);
  }
}
