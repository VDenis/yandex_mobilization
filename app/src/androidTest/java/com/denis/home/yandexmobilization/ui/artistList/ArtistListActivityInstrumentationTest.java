package com.denis.home.yandexmobilization.ui.artistList;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.denis.home.yandexmobilization.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Denis on 23.04.2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ArtistListActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<ArtistListActivity> mActivityRule = new ActivityTestRule<>(
            ArtistListActivity.class);

    @Test
    public void testShowData() {
/*        onView(withId(R.id.artist_list))
                .check(matches(hasDescendant(withText("Some text"))));  */

        onView(withId(R.id.artist_list))
                .check(matches(hasDescendant(withText(R.id.list_item_image))));
    }

    @Test
    public void showNoDataMessage() {
        onView(withText(mActivityRule.getActivity().getString(R.string.empty_artists_list_no_network)));
    }

    @Test
    public void showDetailPage() {
/*        onView(withId(R.id.artist_list)).perform(
                scrollTo(hasDescendant(withText(newNoteDescription))));

        onView(nthChildOf(withId(R.id.artist_list), 0)
                .check(matches(hasDescendant(withText("Some text"))))*/

        onView(withId(R.id.artist_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withText(R.id.detail_artist_genres));
    }

    public void testOnCreate() throws Exception {

    }

    public void testOnPause() throws Exception {

    }

    public void testOnResume() throws Exception {

    }

    public void testOnCreateOptionsMenu() throws Exception {

    }

    public void testOnOptionsItemSelected() throws Exception {

    }

    public void testOnCreateLoader() throws Exception {

    }

    public void testOnLoadFinished() throws Exception {

    }

    public void testOnLoaderReset() throws Exception {

    }
}