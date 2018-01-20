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
import me.aribon.mapsample.backend.AddressManager;
import me.aribon.mapsample.business.AddressBus;
import me.aribon.mapsample.business.LocationBus;
import me.aribon.mapsample.ui.base.BaseActivity;
import me.aribon.mapsample.utils.AddressUtils;

public class MainActivity extends BaseActivity {

  @Override
  public int getLayoutResource() {
    return R.layout.activity_main;
  }
}
