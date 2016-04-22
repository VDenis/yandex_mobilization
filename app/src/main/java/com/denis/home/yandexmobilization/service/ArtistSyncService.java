package com.denis.home.yandexmobilization.service;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.denis.home.yandexmobilization.data.ArtistColumns;
import com.denis.home.yandexmobilization.data.ArtistProvider;
import com.denis.home.yandexmobilization.json.Artist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class ArtistSyncService extends IntentService {
    private final static String TAG = ArtistSyncService.class.getSimpleName();

    // IntentService can perform next actions: ACTION_SYNC
    public static final String ACTION_SYNC = "com.denis.home.yandexmobilization.service.action.SYNC";

    // Parameters EXTRA_URL - base url
    public static final String EXTRA_URL = "com.denis.home.yandexmobilization.service.extra.URL";

    private Context mContext;

    public ArtistSyncService() {
        super("ArtistSyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (mContext == null) {
            mContext = this;
        }

        if (intent != null) {
            final String action = intent.getAction();
            // Check action
            if (ACTION_SYNC.equals(action)) {
                // Get link parameter
                final String param1 = intent.getStringExtra(EXTRA_URL);
                handleActionSync(param1);
            } else {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSync(String targetUrl) {
        Log.d(TAG, "handleActionSync: ");

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String artistJsonStr = null;

        try {
            Uri builtUri = Uri.parse(targetUrl).buildUpon().build();
            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                // TODO SERVER_DOWN
                return;
            }
            artistJsonStr = buffer.toString();

            deleteOldData(); // Delete previous data
            getArtistDataFromJson(artistJsonStr);
            // TODO STATUS_OK
        } catch (IOException e) {
            // TODO Server doen't exist SERVER_DOWN
            Log.e(TAG, "Error ", e);
        } catch (JsonSyntaxException e) {
            // TODO Server sent bad json SERVER_INVALID
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
    }

    private void deleteOldData() {
        mContext.getContentResolver().delete(ArtistProvider.Artists.CONTENT_URI, null, null);
        Log.d(TAG, "deleteOldData: Delete old data in database");
    }

    // Parse json and store artists into database
    public void getArtistDataFromJson(String artistJsonStr) throws JsonSyntaxException {
        // Deserialize json into pojo
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Artist[] artists = gson.fromJson(artistJsonStr, Artist[].class);

        // Prepare data
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        for (Artist artist : artists) {
            batchOperations.add(buildBatchOperation(artist));
        }

        // Store data into database
        try {
            mContext.getContentResolver()
                    .applyBatch(ArtistProvider.AUTHORITY, batchOperations);
            Log.d(TAG, "getArtistDataFromJson: Insert Batch into database - " + artists.length);
        } catch (RemoteException | OperationApplicationException e) {
            e.printStackTrace();
        }

/*        for (Artist artist : artists) {
            ContentValues contentValues = ContentProviderValues(artist);
            int updated = getContentResolver()
                    .update(ArtistProvider.Artists.CONTENT_URI, contentValues, null, null);
            if (updated == 0) {
                getContentResolver()
                        .insert(ArtistProvider.Artists.CONTENT_URI, contentValues);
            }
        }*/
    }

    private ContentProviderOperation buildBatchOperation(Artist artist) {
        ContentProviderOperation.Builder builder = ContentProviderOperation
                .newInsert(ArtistProvider.Artists.CONTENT_URI);
        builder.withValue(ArtistColumns.ID, artist.getId());
        builder.withValue(ArtistColumns.NAME, artist.getName());
        builder.withValue(ArtistColumns.GENRES, TextUtils.join(", ", artist.getGenres()));
        builder.withValue(ArtistColumns.TRACKS, artist.getTracks());
        builder.withValue(ArtistColumns.ALBUMS, artist.getAlbums());
        builder.withValue(ArtistColumns.LINK, artist.getLink());
        builder.withValue(ArtistColumns.DESCRIPTION, artist.getDescription());
        builder.withValue(ArtistColumns.SMALL, artist.getCover().getSmall());
        builder.withValue(ArtistColumns.BIG, artist.getCover().getBig());
        return builder.build();
    }

/*    private ContentValues ContentProviderValues(Artist artist) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ArtistColumns.ID, artist.getId());
                contentValues.put(ArtistColumns.NAME, artist.getName());
        contentValues.put(ArtistColumns.GENRES, TextUtils.join(", ", artist.getGenres()));
        contentValues.put(ArtistColumns.TRACKS, artist.getTracks());
        contentValues.put(ArtistColumns.ALBUMS, artist.getAlbums());
        contentValues.put(ArtistColumns.LINK, artist.getLink());
        contentValues.put(ArtistColumns.DESCRIPTION, artist.getDescription());
        contentValues.put(ArtistColumns.SMALL, artist.getCover().getSmall());
        contentValues.put(ArtistColumns.BIG, artist.getCover().getBig());
        return contentValues;
    }*/
}
