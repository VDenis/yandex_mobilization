package com.denis.home.yandexmobilization.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Denis on 20.04.2016.
 */
public class ArtistSyncServiceHelper {
    /**
     * Starts this service to perform action Foo with the given parameters. If
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

    // TODO change
    private final static String jsonLink = "https://dl.dropboxusercontent.com/u/55518874/YandexMobilizationJson_200416/artists.json";

    public static void startActionSync(Context context) {
        startActionSync(context, jsonLink);
    }
}
