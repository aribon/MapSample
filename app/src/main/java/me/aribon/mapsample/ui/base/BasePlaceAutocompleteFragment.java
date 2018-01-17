package me.aribon.mapsample.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

import me.aribon.mapsample.utils.ResUtils;

/**
 * @Author: aribon
 * @Date: 17/01/2018
 */

public abstract class BasePlaceAutocompleteFragment extends SupportPlaceAutocompleteFragment
        implements GoogleApiClient.OnConnectionFailedListener {

    private BaseActivity activity;

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResource(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view);
        initializeData();
        initializeView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient
                .Builder(activity)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    public BaseActivity getParentActivity() {
        return activity;
    }

    public abstract int getLayoutResource();

    public void findView(View view) {

    }

    public void initializePresenter() {

    }

    public void initializeData() {

    }

    public void initializeView() {

    }

    public void showLoading() {

    }

    public void hideLoading() {

    }

    public void showMessage(String message) {

    }

    public void showMessage(@StringRes int resId) {

    }

    public void showToastMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public void showToastMessage(@StringRes int resId) {
        Toast.makeText(activity, ResUtils.getString(resId), Toast.LENGTH_LONG).show();
    }

    public void showKeyboard() {

    }

    public void hideKeyboard() {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
