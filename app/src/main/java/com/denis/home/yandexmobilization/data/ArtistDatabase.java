package com.denis.home.yandexmobilization.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Denis on 20.04.2016.
 */
@Database(version = ArtistDatabase.VERSION)
public class ArtistDatabase {
    public static final int VERSION = 1;

    @Table(ArtistColumns.class)
    public static final String Artists = "artists";

    private ArtistDatabase() {
    }
}
