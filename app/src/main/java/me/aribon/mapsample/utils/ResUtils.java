package me.aribon.mapsample.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;

import me.aribon.mapsample.AppApplication;

/**
 * @Author: aribon
 * @Date: 15/01/2018
 */

public class ResUtils {

  public static String getString(@StringRes int id) {
    return getAppContext().getString(id);
  }

  public static String getString(@StringRes int id, Object... formatArgs) {
    return getAppContext().getString(id, formatArgs);
  }

  public static Drawable getDrawable(@DrawableRes int id) {
    return AppCompatResources.getDrawable(getAppContext(), id);
  }

  public static int getColor(@ColorRes int id) {
    return ContextCompat.getColor(getAppContext(), id);
  }

  public static Context getAppContext() {
    return AppApplication.getInstance().getApplicationContext();
  }
}

