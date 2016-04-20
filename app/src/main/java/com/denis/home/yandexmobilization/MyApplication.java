package com.denis.home.yandexmobilization;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Denis on 20.04.2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
