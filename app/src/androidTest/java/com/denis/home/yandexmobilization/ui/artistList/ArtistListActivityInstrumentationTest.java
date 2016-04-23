package com.denis.home.yandexmobilization.ui.artistList;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.denis.home.yandexmobilization.R;
import com.denis.home.yandexmobilization.data.ArtistDatabase;
import com.denis.home.yandexmobilization.service.IntentServiceIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Denis on 23.04.2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ArtistListActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<ArtistListActivity> mActivityRule = new ActivityTestRule<>(
            ArtistListActivity.class);


    Activity activity;
    // Ideling Intent Service
    private IntentServiceIdlingResource idlingResource;

    @Before
    public void registerIntentServiceIdlingResource() {
        Instrumentation instrumentation = getInstrumentation();
        idlingResource = new IntentServiceIdlingResource(instrumentation.getTargetContext());
        Espresso.registerIdlingResources(idlingResource);
    }

    @After
    public void unregisterIntentServiceIdlingResource() {
        Espresso.unregisterIdlingResources(idlingResource);
    }

    // Delete database
    void deleteTheDatabase() {
        getInstrumentation().getTargetContext().getDatabasePath(ArtistDatabase.Artists).delete();
    }

    // Show First screen - assert it
    @Test
    public void showArtistListPage() {
        onView(withId(R.id.artist_list))
                .check(matches(isDisplayed()));
    }

    // Show First screen - assert it, click on list item -> go to Second screen - assert it
    @Test
    public void showDetailPage() {
        onView(withId(R.id.artist_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.detail_artist_genres))
                .check(matches(isDisplayed()));
    }
}