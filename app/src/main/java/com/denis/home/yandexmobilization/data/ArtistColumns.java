package com.denis.home.yandexmobilization.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Denis on 20.04.2016.
 */
public class ArtistColumns {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    final String _ID = "_id";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    final String ID = "id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    final String GENRES = "genres";

    @DataType(DataType.Type.TEXT)
    @NotNull
    final String NAME = "name";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    final String TRACKS = "tracks";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    final String ALBUMS = "albums";

    @DataType(DataType.Type.TEXT)
    @NotNull
    final String LINK = "link";

    @DataType(DataType.Type.TEXT)
    @NotNull
    final String DESCRIPTION = "description";

    @DataType(DataType.Type.TEXT)
    @NotNull
    final String SMALL = "small";

    @DataType(DataType.Type.TEXT)
    @NotNull
    final String BIG = "big";
}
