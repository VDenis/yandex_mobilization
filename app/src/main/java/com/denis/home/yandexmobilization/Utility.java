package com.denis.home.yandexmobilization;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Denis on 21.04.2016.
 */
public class Utility {
    /**
     * Download image into imageView
     * @param context activity context
     * @param link download image
     * @param imageView put result into imageView
     */
    public static void downloadImage(Context context, String link, ImageView imageView, Callback callback) {
        Picasso.with(context)
                .load(link)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, callback);
    }

    /**
     * Get quantity string
     * @param context context
     * @param artistAlbumsCount number of albums
     * @param artistTracksCount number of tracks
     * @return result string, concatenate albums quantity plus tracks quantity
     */
    public static String getPluralsTracksAndAlbumsString(Context context, String artistAlbumsCount, String artistTracksCount) {
        int albumsCount = Integer.valueOf(artistAlbumsCount);
        int tracksCount = Integer.valueOf(artistTracksCount);
        String quantityAlbumsCount = context.getResources().getQuantityString(R.plurals.plurals_albums, albumsCount, albumsCount);
        String quantityTracksCount = context.getResources().getQuantityString(R.plurals.plurals_tracks, tracksCount, tracksCount);
        String result = String.format(context.getResources().getString(R.string.artist_tracks_and_albums), quantityAlbumsCount, quantityTracksCount);
        return result;
    }

    /**
     * Returns true if the network is available or about to become available.
     *
     * @param c Context used to get the ConnectivityManager
     * @return true if the network is available
     */
    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
