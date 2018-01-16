package me.aribon.mapsample.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.aribon.mapsample.utils.ResUtils;

/**
 * Created by anthony.ribon
 * On 22/07/2017
 */

public abstract class BaseFragment extends Fragment {

    BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();
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
}
