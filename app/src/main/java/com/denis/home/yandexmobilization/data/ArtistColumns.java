package com.denis.home.yandexmobilization.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DefaultValue;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Denis on 20.04.2016.
 */
public class ArtistColumns {
    public static final String EMPTY_STRING = "";
    public static final int ZERO_INT = 0;

    private static final String DEFAULT_VALUE_TEXT = "''";
    private static final String DEFAULT_VALUE_INTEGER = "0"; // Number, integer, like 0,1,2

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    @DefaultValue(DEFAULT_VALUE_INTEGER)
    @Unique(onConflict = ConflictResolutionType.REPLACE)
    public static final String ID = "id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    @DefaultValue(DEFAULT_VALUE_TEXT)
    public static final String GENRES = "genres";

    @DataType(DataType.Type.TEXT)
    @NotNull
    @DefaultValue(DEFAULT_VALUE_TEXT)
    public static final String NAME = "name";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    @DefaultValue(DEFAULT_VALUE_INTEGER)
    public static final String TRACKS = "tracks";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    @DefaultValue(DEFAULT_VALUE_INTEGER)
    public static final String ALBUMS = "albums";

    @DataType(DataType.Type.TEXT)
    @NotNull
    @DefaultValue(DEFAULT_VALUE_TEXT)
    public static final String LINK = "link";

    @DataType(DataType.Type.TEXT)
    @NotNull
    @DefaultValue(DEFAULT_VALUE_TEXT)
    public static final String DESCRIPTION = "description";

    @DataType(DataType.Type.TEXT)
    @NotNull
    @DefaultValue(DEFAULT_VALUE_TEXT)
    public static final String SMALL = "small";

    @DataType(DataType.Type.TEXT)
    @NotNull
    @DefaultValue(DEFAULT_VALUE_TEXT)
    public static final String BIG = "big";
}
