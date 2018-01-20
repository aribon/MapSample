package me.aribon.mapsample.business;

import com.google.android.gms.maps.model.LatLng;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

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

    public Observable<LatLng> register(Class<LatLng> eventClass) {
        return busSubject
                .filter(event -> event.getClass().equals(eventClass))
                .map(object -> (LatLng) object);
    }

    public Observable<LatLng> register() {
        return busSubject
                .map(object -> (LatLng) object)
                .subscribeOn(Schedulers.io());
    }

    public void send(LatLng event) {
        busSubject.accept(event);
    }
}
