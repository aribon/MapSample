package me.aribon.mapsample.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

import me.aribon.mapsample.R;
import me.aribon.mapsample.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

  @Override
  public int getLayoutResource() {
    return R.layout.activity_main;
  }

}
