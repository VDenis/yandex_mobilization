package com.denis.home.yandexmobilization.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Denis on 20.04.2016.
 */
@ContentProvider(authority = ArtistProvider.AUTHORITY, database = ArtistDatabase.class)
public class ArtistProvider {
    public static final String AUTHORITY = "com.denis.home.yandexmobilization.data.ArtistProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    interface Path {
        String Artists = "artists";
    }

    @TableEndpoint(table = ArtistDatabase.Artists)
    public static class Artists {
        @ContentUri(
                path = Path.Artists,
                type = "vnd.android.cursor.dir/artist"
        )
        public static final Uri CONTENT_URI = buildUri(Path.Artists);

        @InexactContentUri(
                name = "NOTE_ID",
                path = Path.Artists + "/*",
                type = "vnd.android.cursor.item/artist",
                whereColumn = ArtistColumns._ID,
                pathSegment = 1
        )
        public static Uri withId(int id) {
            return buildUri(Path.Artists, Integer.toString(id));
        }
    }
}
