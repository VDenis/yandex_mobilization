package com.denis.home.yandexmobilization.json;

import com.denis.home.yandexmobilization.data.ArtistColumns;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Class for json serialisation
public class Cover {

    @SerializedName("small")
    @Expose
    private String small;
    @SerializedName("big")
    @Expose
    private String big;

    /**
     * No args constructor for use in serialization
     */
    public Cover() {
        this.small = ArtistColumns.EMPTY_STRING;
        this.big = ArtistColumns.EMPTY_STRING;
    }

    /**
     * @param big
     * @param small
     */
    public Cover(String small, String big) {
        this.small = small;
        this.big = big;
    }

    /**
     * @return The small
     */
    public String getSmall() {
        return small;
    }

    /**
     * @param small The small
     */
    public void setSmall(String small) {
        this.small = small;
    }

    /**
     * @return The big
     */
    public String getBig() {
        return big;
    }

    /**
     * @param big The big
     */
    public void setBig(String big) {
        this.big = big;
    }

}


