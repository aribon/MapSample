package me.aribon.mapsample.business.bus;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import me.aribon.mapsample.business.event.AddressEvent;

/**
 * @Author: aribon
 * @Date: 21/01/2018
 */

public class AddressBus {

  private static volatile AddressBus instance;

  private final Relay<Object> busSubject = PublishRelay.create().toSerialized();

  public static AddressBus getInstance() {
    if (instance == null) {
      synchronized (AddressBus.class) {
        if (instance == null) {
          instance = new AddressBus();
        }
      }
    }

    return instance;
  }

  private AddressBus() {
    if (instance != null) {
      throw new RuntimeException(
          "Use getInstance method to get the single instance of this class.");
    }
  }

  /**
   * Subscribe to the addressBus to be updated on any new address sent
   *
   * @return the observable contains the AddressEvent
   */
  public Observable<AddressEvent> register() {
    return busSubject
        .map(object -> (AddressEvent) object)
        .subscribeOn(Schedulers.io());
  }

  /**
   * Inform the addressBus that address was be updated
   *
   * @param event
   */
  public void send(AddressEvent event) {
    busSubject.accept(event);
  }

}
