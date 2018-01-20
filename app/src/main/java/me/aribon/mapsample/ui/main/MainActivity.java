package me.aribon.mapsample.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.aribon.mapsample.R;
import me.aribon.mapsample.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
//                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//
//        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
//                .setCountry("FR")
//                .build();
//
//        autocompleteFragment.setFilter(typeFilter);
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                Log.i(TAG, "Coordinate: " + place.getLatLng().latitude + ", " + place.getLatLng().longitude);
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: " + status);
//            }
//        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }
}
