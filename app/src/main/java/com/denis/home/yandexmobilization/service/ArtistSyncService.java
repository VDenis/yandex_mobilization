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
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ArtistSyncService extends IntentService {
    private final static String TAG = ArtistSyncService.class.getSimpleName();

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_SYNC = "com.denis.home.yandexmobilization.service.action.SYNC";
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
            if (ACTION_SYNC.equals(action)) {
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
                // Stream was empty.  No point in parsing.
                return;
            }
            artistJsonStr = buffer.toString();

            // deleteOldData(); // User in sql ON CONFLICT update
            getArtistDataFromJson(artistJsonStr);
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
        } catch (JsonSyntaxException e) {
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
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Artist[] artists = gson.fromJson(artistJsonStr, Artist[].class);

        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        for (Artist artist : artists) {
            batchOperations.add(buildBatchOperation(artist));
        }
        try {
            mContext.getContentResolver().applyBatch(ArtistProvider.AUTHORITY,
                    batchOperations);
            Log.d(TAG, "getArtistDataFromJson: Insert Batch into database - " +artists.length);
        } catch (RemoteException | OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    private ContentProviderOperation buildBatchOperation(Artist artist) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                ArtistProvider.Artists.CONTENT_URI);
        builder.withValue(ArtistColumns.ID, artist.getId());
        builder.withValue(ArtistColumns.NAME, artist.getName());
        builder.withValue(ArtistColumns.GENRES, TextUtils.join(", ", artist.getGenres()));
        builder.withValue(ArtistColumns.TRACKS, artist.getTracks());
        builder.withValue(ArtistColumns.ALBUMS, artist.getAlbums());

        // TODO Database error null field - Solved
        builder.withValue(ArtistColumns.LINK, artist.getLink());
        //builder.withValue(ArtistColumns.LINK, artist.getLink() != null ? artist.getLink() : "" );
/*        if (artist.getLink() != null) {
            builder.withValue(ArtistColumns.LINK, artist.getLink());
        } else {
            Log.w(TAG, "buildBatchOperation: " + artist.getName());
        }*/

        builder.withValue(ArtistColumns.DESCRIPTION, artist.getDescription());
        builder.withValue(ArtistColumns.SMALL, artist.getCover().getSmall());
        builder.withValue(ArtistColumns.BIG, artist.getCover().getBig());
        return builder.build();
    }
}
