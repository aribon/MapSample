package me.aribon.mapsample.backend;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import me.aribon.mapsample.AppApplication;

/**
 * @Author: aribon
 * @Date: 16/01/2018
 */

public class LocationManager {

    private static volatile LocationManager instance;

    public static LocationManager getInstance() {
        if (instance == null) {
            synchronized (LocationManager.class) {
                if (instance == null) {
                    instance = new LocationManager();
                }
            }
        }

        return instance;
    }

    private LocationManager() {
        if (instance != null) {
            throw new RuntimeException(
                "Use getInstance method to get the single instance of this class.");
        }
    }

    private FusedLocationProviderClient mFusedLocationClient;

    private final Relay<Object> busSubject = PublishRelay.create().toSerialized();

    public void fetchCurrentPosition(OnSuccessListener<Location> successListener) {
        if (mFusedLocationClient == null) {
            mFusedLocationClient = LocationServices
                .getFusedLocationProviderClient(AppApplication.getInstance().getApplicationContext());
        }

        if (ActivityCompat.checkSelfPermission(AppApplication.getInstance().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(AppApplication.getInstance().getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(successListener);
    }

    @Nullable
    public Observable<Address> fetchAddressFromLocation(LatLng latLng) {
        return fetchAddressFromLocation(latLng.latitude, latLng.longitude);
    }

    @Nullable
    public Observable<Address> fetchAddressFromLocation(double latitude, double longitude) {
        return Relay.create(
            new ObservableOnSubscribe<Address>() {
                @Override
                public void subscribe(ObservableEmitter<Address> emitter) throws Exception {
                    Address address = null;

                    Geocoder geocoder = new Geocoder(AppApplication.getInstance().getApplicationContext(),
                        Locale.ENGLISH);

                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

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

    @Nullable
    public Observable<Address> fetchAddressFromName(String addressName) {
        return Relay.create(
            new ObservableOnSubscribe<Address>() {
                @Override
                public void subscribe(ObservableEmitter<Address> emitter) throws Exception {
                    Address address = null;

                    Geocoder geocoder = new Geocoder(AppApplication.getInstance().getApplicationContext(),
                        Locale.ENGLISH);

                    try {
                        List<Address> addresses = geocoder.getFromLocationName(addressName, 1);

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
        );
    }

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
