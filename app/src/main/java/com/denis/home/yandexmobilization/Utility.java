package com.denis.home.yandexmobilization;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Denis on 21.04.2016.
 */
public class Utility {
    public static void downloadImage(Context context, String link, ImageView imageView) {
        Picasso.with(context)
                .load(link)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static String getPluralsTracksAndAlbumsString(Context context, String artistAlbumsCount, String artistTracksCount) {
        int albumsCount = Integer.valueOf(artistAlbumsCount);
        int tracksCount = Integer.valueOf(artistTracksCount);
        //String quantityAlbumsCount = mArtistListActivity.getResources().getQuantityString(R.plurals.plurals_test, 1, 1);
        String quantityAlbumsCount = context.getResources().getQuantityString(R.plurals.plurals_albums, albumsCount, albumsCount);
        //String quantityTracksCount = mArtistListActivity.getResources().getQuantityString(R.plurals.plurals_test, 20, 20);
        String quantityTracksCount = context.getResources().getQuantityString(R.plurals.plurals_tracks, tracksCount, tracksCount);
        String result = String.format(context.getResources().getString(R.string.artist_tracks_and_albums), quantityAlbumsCount, quantityTracksCount);
        return result;
    }
}
