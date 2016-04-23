package com.denis.home.yandexmobilization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Denis on 23.04.2016.
 */
public class CoverTest {

    private Cover mCover;
    private final String testStr = "test string";

    @Before
    public void setUp() throws Exception {
        mCover = new Cover();
    }

    @Test
    public void testGetSmall() throws Exception {
        assertNotNull(mCover.getSmall());
    }

    @Test
    public void testSetSmall() throws Exception {
        assertNotEquals(mCover.getSmall(), testStr);
        mCover.setSmall(testStr);
        assertEquals(mCover.getSmall(), testStr);
    }

    @Test
    public void testGetBig() throws Exception {
        assertNotNull(mCover.getBig());
    }

    @Test
    public void testSetBig() throws Exception {
        assertNotEquals(mCover.getBig(), testStr);
        mCover.setBig(testStr);
        assertEquals(testStr, mCover.getBig());
    }

    // Test deserilization
    @Test
    public void testDeserilization() throws Exception {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String coverJsonStr = "{" +
                "\"small\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\"," +
                "\"big\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"" +
                "}";
        Cover result = gson.fromJson(coverJsonStr, Cover.class);
        assertEquals("http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300", result.getSmall());
        assertEquals("http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000", result.getBig());
    }

    // Test serialization
    @Test
    public void testSerilization() throws Exception {
        Gson gson = new Gson();

        String small = "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300";
        String big = "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000";
        Cover cover = new Cover(small, big);

        String coverJsonStr = "{" +
                "\"small\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\"," +
                "\"big\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"" +
                "}";

        String result = gson.toJson(cover);

        assertEquals(coverJsonStr, result);
    }
}