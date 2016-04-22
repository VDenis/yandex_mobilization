package com.denis.home.yandexmobilization.ui.artistList;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.denis.home.yandexmobilization.R;
import com.denis.home.yandexmobilization.Utility;
import com.denis.home.yandexmobilization.data.ArtistProvider;
import com.denis.home.yandexmobilization.service.ArtistSyncServiceHelper;
import com.denis.home.yandexmobilization.ui.artistDetail.ArtistDetailActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * An activity representing a list of Artists. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ArtistDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ArtistListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private static final int CURSOR_LOADER_ID = 0;

    // RecyclerView and Adapter
    private SimpleItemRecyclerViewAdapter mSimpleItemRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    // Save RecyclerView state use onSaveInstanceState
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mRecyclerView = (RecyclerView ) findViewById(R.id.artist_list);
        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);

        if (findViewById(R.id.artist_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        if (savedInstanceState == null) {
            startSync();
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        // add divider to the Recycler View
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        ViewSwitcher viewSwitcher = (ViewSwitcher) findViewById(R.id.switcher);
        if (viewSwitcher != null) {
            viewSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left)); // slide_in_left
            viewSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_right)); // slide_out_right
        }
        mSimpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(this, viewSwitcher, mTwoPane);

        recyclerView.setAdapter(mSimpleItemRecyclerViewAdapter);

        // Launch Loader
        getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
    }

/*    @Override
    protected void onStart() {
        super.onStart();

        startSync();
    }*/

    private void startSync() {
        // start background synchronization service
        // check network connection
        if (Utility.isNetworkAvailable(this)) {
            ArtistSyncServiceHelper.startActionSync(this);
        } else {
            Toast.makeText(ArtistListActivity.this, getString(R.string.empty_artists_list_no_network), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            startSync();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                ArtistProvider.Artists.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mSimpleItemRecyclerViewAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSimpleItemRecyclerViewAdapter.swapCursor(null);
    }
}
