package com.denis.home.yandexmobilization.json;

import com.denis.home.yandexmobilization.data.ArtistColumns;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

// Class for json serialisation
public class Artist {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();
    @SerializedName("tracks")
    @Expose
    private Integer tracks;
    @SerializedName("albums")
    @Expose
    private Integer albums;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cover")
    @Expose
    private Cover cover;

    /**
     * No args constructor for use in serialization
     */
    public Artist() {
        this.id = ArtistColumns.ZERO_INT;
        this.name = ArtistColumns.EMPTY_STRING;

        this.tracks = ArtistColumns.ZERO_INT;
        this.albums = ArtistColumns.ZERO_INT;
        this.link = ArtistColumns.EMPTY_STRING;
        this.description = ArtistColumns.EMPTY_STRING;
    }

    /**
     * @param id
     * @param cover
     * @param genres
     * @param description
     * @param link
     * @param albums
     * @param name
     * @param tracks
     */
    public Artist(Integer id, String name, List<String> genres, Integer tracks, Integer albums, String link, String description, Cover cover) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;
        this.cover = cover;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * @param genres The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    /**
     * @return The tracks
     */
    public Integer getTracks() {
        return tracks;
    }

    /**
     * @param tracks The tracks
     */
    public void setTracks(Integer tracks) {
        this.tracks = tracks;
    }

    /**
     * @return The albums
     */
    public Integer getAlbums() {
        return albums;
    }

    /**
     * @param albums The albums
     */
    public void setAlbums(Integer albums) {
        this.albums = albums;
    }

    /**
     * @return The link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The cover
     */
    public Cover getCover() {
        return cover;
    }

    /**
     * @param cover The cover
     */
    public void setCover(Cover cover) {
        this.cover = cover;
    }

}
