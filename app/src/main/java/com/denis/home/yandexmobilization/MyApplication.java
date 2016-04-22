package com.denis.home.yandexmobilization;

import android.app.Application;
import android.net.http.HttpResponseCache;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

/**
 * Created by Denis on 20.04.2016.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        // Use developer tools only in devel configuration
        if (BuildConfig.FLAVOR.equals("devel")) {
            Stetho.initializeWithDefaults(this);
            Picasso.with(this).setIndicatorsEnabled(true);
        }

        // set up http cache
        try {
            File httpCacheDir = new File(getCacheDir(), "http");
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            Log.d(TAG, "HTTP response cache installation failed:" + e);
        }
    }

    protected void onStop() {
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }
    }
}
