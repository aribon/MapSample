package me.aribon.mapsample.ui.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import me.aribon.mapsample.AppApplication;
import me.aribon.mapsample.R;
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
    new AlertDialog.Builder(AppApplication.getInstance())
        .setMessage(message)
        .setPositiveButton(
            ResUtils.getString(R.string.generic_ok),
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
              }
            })
        .create()
        .show();
  }

  public void showMessage(@StringRes int resId) {
    showMessage(ResUtils.getString(resId));
  }

  public void showToastMessage(String message) {
    Toast.makeText(AppApplication.getInstance(), message, Toast.LENGTH_LONG).show();
  }

  public void showToastMessage(@StringRes int resId) {
    showToastMessage(ResUtils.getString(resId));
  }

  public void showKeyboard() {

  }

  public void hideKeyboard() {

  }
}
