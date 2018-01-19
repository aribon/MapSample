package me.aribon.mapsample.ui.main;

import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.aribon.mapsample.R;
import me.aribon.mapsample.backend.LocationManager;
import me.aribon.mapsample.ui.base.BaseActivity;
import me.aribon.mapsample.utils.AddressUtils;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Disposable locationDisposable = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Coordinate: " + place.getLatLng().latitude + ", " + place.getLatLng().longitude);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        locationDisposable = LocationManager.getInstance().register()
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(
                new Function<LatLng, ObservableSource<Address>>() {
                    @Override
                    public ObservableSource<Address> apply(LatLng latLng) throws Exception {
                        return LocationManager.getInstance().fetchAddressFromLocation(latLng);
                    }
                }
            )
            .subscribe(
                new Consumer<Address>() {
                    @Override
                    public void accept(Address address) throws Exception {
                        autocompleteFragment.setText(AddressUtils.formatAddress(address));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showToastMessage(throwable.getMessage());
                    }
                }
            );
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        if (!locationDisposable.isDisposed()) {
            locationDisposable.dispose();
        }

        locationDisposable = null;
        super.onDestroy();
    }
}
