package me.aribon.mapsample.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import me.aribon.mapsample.utils.ResUtils;

/**
 * @Author: aribon
 * @Date: 15/01/2018
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        findView();
        initializePresenter();
        initializeData();
        initializeView();
    }

    public abstract int getLayoutResource();

    public void findView() {

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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showToastMessage(@StringRes int resId) {
        Toast.makeText(this, ResUtils.getString(resId), Toast.LENGTH_LONG).show();
    }

    public void showKeyboard() {

    }

    public void hideKeyboard() {

    }
}
