package com.denis.home.yandexmobilization.ui.artistList;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.denis.home.yandexmobilization.R;
import com.denis.home.yandexmobilization.Utility;
import com.denis.home.yandexmobilization.data.ArtistColumns;
import com.denis.home.yandexmobilization.data.ArtistProvider;
import com.denis.home.yandexmobilization.ui.artistDetail.ArtistDetailActivity;
import com.denis.home.yandexmobilization.ui.artistDetail.ArtistDetailFragment;
import com.squareup.picasso.Callback;

/**
 * Created by Denis on 20.04.2016.
 */
public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private ArtistListActivity mArtistListActivity;
    private ViewSwitcher mViewSwitcher;
    private final boolean mTwoPane;
    private Cursor mCursor;
    private boolean isRefreshing;

    public SimpleItemRecyclerViewAdapter(ArtistListActivity artistListActivity, ViewSwitcher viewSwitcher, boolean twoPane) {
        mArtistListActivity = artistListActivity;
        mViewSwitcher = viewSwitcher;
        mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_artist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        final int databaseId = mCursor.getInt(mCursor.getColumnIndex(ArtistColumns._ID));

        final String artistImageLink = mCursor.getString(mCursor.getColumnIndex(ArtistColumns.SMALL));
        final String artistName = mCursor.getString(mCursor.getColumnIndex(ArtistColumns.NAME));
        final String artistGenres = mCursor.getString(mCursor.getColumnIndex(ArtistColumns.GENRES));
        final String artistTracksCount = mCursor.getString(mCursor.getColumnIndex(ArtistColumns.TRACKS));
        final String artistAlbumsCount = mCursor.getString(mCursor.getColumnIndex(ArtistColumns.ALBUMS));

        Utility.downloadImage(mArtistListActivity, artistImageLink, holder.mImageView, new Callback.EmptyCallback());

        // Set content description to the artist image
        String imageDescription = String.format(mArtistListActivity.getResources().getString(R.string.a11n_artist_photo_name), artistName);
        holder.mImageView.setContentDescription(imageDescription);

        holder.mArtistNameView.setText(artistName);
        holder.mArtistGenresView.setText(artistGenres);

        String result = Utility.getPluralsTracksAndAlbumsString(mArtistListActivity, artistAlbumsCount, artistTracksCount);
        holder.mArtistTracksAndAlbumsView.setText(result);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // disable transition to detail activity then data refreshing
                if (!isRefreshing) {
                    Uri artistIdUri = ArtistProvider.Artists.withId(databaseId);
                    if (mTwoPane) {
                        ArtistDetailFragment fragment = ArtistDetailFragment.newInstance(artistIdUri, artistName);

                        mArtistListActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.artist_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ArtistDetailActivity.class).setData(artistIdUri);
                        intent.putExtra(ArtistDetailFragment.DETAIL_TITLE, artistName);

/*                        ActivityOptionsCompat options =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(mArtistListActivity);
                        ActivityCompat.startActivity(mArtistListActivity, intent, options.toBundle());*/
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mArtistListActivity).toBundle());
                        } else {
                            context.startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();

        if (newCursor != null && newCursor.getCount() > 0) {
            if (mViewSwitcher != null &&  R.id.artist_list == mViewSwitcher.getNextView().getId()) {
                isRefreshing = false;
                mViewSwitcher.showNext();
            }
        } else if (mViewSwitcher != null && R.id.listview_artist_empty == mViewSwitcher.getNextView().getId()) {
            isRefreshing = true;
            mViewSwitcher.showNext();
        }
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mArtistNameView;
        public final TextView mArtistTracksAndAlbumsView;
        public final TextView mArtistGenresView;
        public final ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mArtistNameView = (TextView) view.findViewById(R.id.list_item_artist_name);
            mArtistGenresView = (TextView) view.findViewById(R.id.list_item_artist_genres);
            mArtistTracksAndAlbumsView = (TextView) view.findViewById(R.id.list_item_artist_tracks_and_albums);
            mImageView = (ImageView) view.findViewById(R.id.list_item_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mArtistNameView + " " + mArtistGenresView.getText() + "'";
        }
    }
}
