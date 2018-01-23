package me.aribon.mapsample.ui.base;

import android.support.annotation.StringRes;

/**
 * @Author: aribon
 * @Date: 15/01/2018
 */

public interface BaseMvpView {

  void showLoading();

  void hideLoading();

  void showMessage(String message);

  void showMessage(@StringRes int resId);

  void showToastMessage(String message);

  void showToastMessage(@StringRes int resId);

  void showKeyboard();

  void hideKeyboard();
}
