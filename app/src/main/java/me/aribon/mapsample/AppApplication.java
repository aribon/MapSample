package me.aribon.mapsample;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;

import me.aribon.mapsample.utils.ResUtils;

/**
 * @Author: aribon
 * @Date: 15/01/2018
 */

public class AppApplication extends Application {

    private static AppApplication instance;

    public static AppApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Mapbox.getInstance(getApplicationContext(), ResUtils.getString(R.string.mapbox_key));
    }
}
