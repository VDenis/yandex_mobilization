package com.denis.home.yandexmobilization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Denis on 23.04.2016.
 */
public class ArtistTest {

    private Artist mArtist;

    @Before
    public void setUp() throws Exception {
        mArtist = new Artist();
    }

    private final String artistJsonStr = "[{\"id\":1080505,\"name\":\"Tove Lo\",\"genres\":[\"pop\",\"dance\",\"electronics\"],\"tracks\":81,\"albums\":22,\"link\":\"http://www.tove-lo.com/\",\"description\":\"шведская певица и автор песен. Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage на эту песню, который получил название «Stay High». 4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, а 24 сентября этого же года дебютный студийный альбом Queen of the Clouds. Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"}}]";

    // Test deserialization
    @Test
    public void testDeserialization() throws Exception {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Artist[] result = gson.fromJson(artistJsonStr, Artist[].class);
        assertEquals("Tove Lo", result[0].getName());
        assertEquals("1080505", result[0].getId().toString());
        assertEquals("http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000", result[0].getCover().getBig());
    }

    // Test serialization
    @Test
    public void testSerialization() throws Exception {
        Gson gson = new Gson();
        Artist[] artists = new Artist[1];
        artists[0] = new Artist(
                1080505,
                "Tove Lo",
                new ArrayList<String>(){{
                    add("pop"); add("dance"); add("electronics");
                }},
                81,
                22,
                "http://www.tove-lo.com",
                "шведская певица и автор песен. Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage на эту песню, который получил название «Stay High». 4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, а 24 сентября этого же года дебютный студийный альбом Queen of the Clouds. Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.",
                new Cover("http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300", "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000")
        );
        String result = gson.toJson(artists);
        Artist[] resultArray = gson.fromJson(result, Artist[].class);
        assertTrue(artists[0].getDescription().equals(resultArray[0].getDescription()));
    }
}