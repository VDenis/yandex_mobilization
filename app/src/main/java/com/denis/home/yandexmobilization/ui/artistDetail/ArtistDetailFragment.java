package com.denis.home.yandexmobilization.ui.artistDetail;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denis.home.yandexmobilization.R;
import com.denis.home.yandexmobilization.Utility;
import com.denis.home.yandexmobilization.data.ArtistColumns;
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

    private static final String TAG = ArtistDetailFragment.class.getSimpleName();

    public static final String DETAIL_URI = "URI";
    public static final String DETAIL_TITLE = "TITLE";

    // Share functionality
    private static final String YANDEX_MOBILIZATION_SHARE_HASHTAG = " #YandexMobilization";
    private String mShareArtist;

    private Uri mUri;
    private String mTitle;

    private static final int DETAIL_CURSOR_LOADER_ID = 10;

    private TextView mArtistDescriptionView;
    private ImageView mArtistPhotoView;
    private TextView mArtistGenresView;
    private TextView mArtistTracksAndAlbumsView;

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
            /*Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

            if (appBarLayout != null) {
                appBarLayout.setTitle(mTitle);
            }*/
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
        mArtistDescriptionView = (TextView) rootView.findViewById(R.id.artist_description);
        mArtistGenresView = (TextView) rootView.findViewById(R.id.detail_artist_genres);
        mArtistTracksAndAlbumsView = (TextView) rootView.findViewById(R.id.detail_artist_tracks_and_albums);

        return rootView;
    }

    interface DetailFragmentable {
        CollapsingToolbarLayout getCollapsingToolbarLayout();
        ImageView getArtistDetailPhoto();
        FloatingActionButton getShareButton();
    }

    private Intent createShareArtistIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mShareArtist + YANDEX_MOBILIZATION_SHARE_HASHTAG);
        return shareIntent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity().getSupportLoaderManager().initLoader(DETAIL_CURSOR_LOADER_ID, null, this);
        // Find image view in toolbar

        CollapsingToolbarLayout appBarLayout = ((DetailFragmentable) getActivity()).getCollapsingToolbarLayout();
        if (appBarLayout != null) {
            appBarLayout.setTitle(mTitle);
            Log.d(TAG, "onActivityCreated: Update toolbar title");
        }

        mArtistPhotoView = ((DetailFragmentable) getActivity()).getArtistDetailPhoto();

        FloatingActionButton fab = ((DetailFragmentable) getActivity()).getShareButton();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = createShareArtistIntent();
                startActivity(intent);
            }
        });

        if (getLoaderManager().getLoader(DETAIL_CURSOR_LOADER_ID) == null) {
            getLoaderManager().initLoader(DETAIL_CURSOR_LOADER_ID, null, this);
        } else {
            getLoaderManager().restartLoader(DETAIL_CURSOR_LOADER_ID, null, this);
        }

        Log.d(TAG, "onActivityCreated: onActivityCreated");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mUri != null) {
            Log.d(TAG, "onCreateLoader: Create Loader");
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getContext(),
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
            Log.d(TAG, "onLoadFinished: Cursor have elements");

            String artistName = data.getString(data.getColumnIndex(ArtistColumns.NAME));
            String artistDescription = data.getString(data.getColumnIndex(ArtistColumns.DESCRIPTION));
            final String artistImageLink = data.getString(data.getColumnIndex(ArtistColumns.BIG));
            final String artistGenres = data.getString(data.getColumnIndex(ArtistColumns.GENRES));
            final String artistTracksCount = data.getString(data.getColumnIndex(ArtistColumns.TRACKS));
            final String artistAlbumsCount = data.getString(data.getColumnIndex(ArtistColumns.ALBUMS));

            if (mArtistPhotoView != null) {
                Utility.downloadImage(getContext(), artistImageLink, mArtistPhotoView);
                Log.d(TAG, "onLoadFinished: Update toolbar image");
            }
            mArtistDescriptionView.setText(artistDescription);
            mArtistGenresView.setText(artistGenres);
            String result = Utility.getPluralsTracksAndAlbumsString(getContext(), artistAlbumsCount, artistTracksCount);
            mArtistTracksAndAlbumsView.setText(result);

            mShareArtist = String.format("%s%n%n%s%n%s%n%n%s", artistName, artistGenres, result, artistDescription);
        } else {
            Log.d(TAG, "onLoadFinished: Cursor doesn't have elements");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
