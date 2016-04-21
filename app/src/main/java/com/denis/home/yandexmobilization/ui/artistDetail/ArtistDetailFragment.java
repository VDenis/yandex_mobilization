package com.denis.home.yandexmobilization.ui.artistDetail;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denis.home.yandexmobilization.R;
import com.denis.home.yandexmobilization.data.ArtistColumns;
import com.denis.home.yandexmobilization.dummy.DummyContent;
import com.denis.home.yandexmobilization.ui.artistList.ArtistListActivity;

/**
 * A fragment representing a single Artist detail screen.
 * This fragment is either contained in a {@link ArtistListActivity}
 * in two-pane mode (on tablets) or a {@link ArtistDetailActivity}
 * on handsets.
 */
public class ArtistDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    //public static final String ARG_ITEM_ID = "item_id";

    public static final String DETAIL_URI = "URI";
    public static final String DETAIL_TITLE = "TITLE";

    private Uri mUri;
    private String mTitle;

    private static final int DETAIL_CURSOR_LOADER_ID = 0;

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private TextView mArtistNameTextView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArtistDetailFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param artistUri content uri .
     * @return A new instance of fragment NoteDetailFragment.
     */
    public static ArtistDetailFragment newInstance(Uri artistUri, String title) {
        ArtistDetailFragment fragment = new ArtistDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(DETAIL_URI, artistUri);
        args.putString(DETAIL_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }*/
        if (getArguments() != null) {
            mUri = getArguments().getParcelable(DETAIL_URI);
            mTitle = getArguments().getString(DETAIL_TITLE);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mTitle);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_detail, container, false);

        // Show the dummy content as text in a TextView.
/*        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.artist_detail)).setText(mItem.details);
        }*/
        mArtistNameTextView = (TextView) rootView.findViewById(R.id.artist_detail);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity().getSupportLoaderManager().initLoader(DETAIL_CURSOR_LOADER_ID, null, this);
        getLoaderManager().initLoader(DETAIL_CURSOR_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mUri != null) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    null,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            String artistName = data.getString(data.getColumnIndex(ArtistColumns.NAME));
            mArtistNameTextView.setText(artistName);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
