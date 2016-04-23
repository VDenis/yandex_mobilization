package com.denis.home.yandexmobilization.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.denis.home.yandexmobilization.BuildConfig;

/**
 * Created by Denis on 20.04.2016.
 */
public class ArtistSyncServiceHelper {
    private static final String TAG = ArtistSyncServiceHelper.class.getSimpleName();

    /**
     * Starts this service to perform action Sync with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    private static void startActionSync(Context context, String uri) {
        Intent intent = new Intent(context, ArtistSyncService.class);
        intent.setAction(ArtistSyncService.ACTION_SYNC);
        intent.putExtra(ArtistSyncService.EXTRA_URL, uri);
        context.startService(intent);
    }

    private final static String jsonLink = BuildConfig.SERVER_PATH;

    public static void startActionSync(Context context) {
        Log.d(TAG, "startActionSync: Server path " + jsonLink);
        startActionSync(context, jsonLink);
    }
}
