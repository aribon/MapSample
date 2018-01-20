package me.aribon.mapsample.backend;

import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import me.aribon.mapsample.AppApplication;

/**
 * @Author: aribon
 * @Date: 19/01/2018
 */

public class AddressManager {

    @Nullable
    public Observable<Address> fetchAddressFromLocation(double lat, double lng) {
        return Observable.create(
                new ObservableOnSubscribe<Address>() {
                    @Override
                    public void subscribe(ObservableEmitter<Address> emitter) throws Exception {
                        Address address = null;

                        Geocoder geocoder = new Geocoder(AppApplication.getInstance().getApplicationContext(),
                                Locale.FRANCE);

                        try {
                            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

                            if (addresses.size() > 0) {
                                address = addresses.get(0);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        emitter.onNext(address);
                        emitter.onComplete();
                    }
                }
        ).subscribeOn(Schedulers.io());
    }
}
