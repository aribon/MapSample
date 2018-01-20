package me.aribon.mapsample.business;

import android.location.Address;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: aribon
 * @Date: 19/01/2018
 */

public class AddressBus {

    private static volatile AddressBus instance;

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

    private final Relay<Object> busSubject = PublishRelay.create().toSerialized();

    public Observable<Address> register() {
        return busSubject
                .map(object -> (Address) object)
                .subscribeOn(Schedulers.io());
    }

    public void send(Address event) {
        busSubject.accept(event);
    }
}
