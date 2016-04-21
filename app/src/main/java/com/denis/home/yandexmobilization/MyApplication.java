package com.denis.home.yandexmobilization;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;

/**
 * Created by Denis on 20.04.2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Picasso.with(this).setIndicatorsEnabled(true);
    }
}
