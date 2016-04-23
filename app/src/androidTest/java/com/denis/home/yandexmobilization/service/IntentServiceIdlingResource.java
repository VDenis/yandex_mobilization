package com.denis.home.yandexmobilization.service;

import android.app.ActivityManager;
import android.content.Context;
import android.support.test.espresso.IdlingResource;

/**
 * Created by Denis on 23.04.2016.
 */
public class IntentServiceIdlingResource implements IdlingResource {
    private final Context context;
    private ResourceCallback resourceCallback;

    public IntentServiceIdlingResource(Context context) {
        this.context = context;
    }

    @Override
    public String getName() {
        return IntentServiceIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = !isIntentServiceRunning();
        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    private boolean isIntentServiceRunning() {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo info : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ArtistSyncService.class.getName().equals(info.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
