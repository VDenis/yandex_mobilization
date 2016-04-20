package com.denis.home.yandexmobilization.ui.artistList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denis.home.yandexmobilization.R;
import com.denis.home.yandexmobilization.data.ArtistColumns;
import com.denis.home.yandexmobilization.ui.artistDetail.ArtistDetailActivity;
import com.denis.home.yandexmobilization.ui.artistDetail.ArtistDetailFragment;
import com.squareup.picasso.Picasso;

/**
 * Created by Denis on 20.04.2016.
 */
public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private ArtistListActivity mArtistListActivity;
    //private final List<DummyContent.DummyItem> mValues;
    private final boolean mTwoPane;
    private Cursor mCursor;

    //public SimpleItemRecyclerViewAdapter(ArtistListActivity artistListActivity, List<DummyContent.DummyItem> items, boolean twoPane) {
    public SimpleItemRecyclerViewAdapter(ArtistListActivity artistListActivity, boolean twoPane) {
        mArtistListActivity = artistListActivity;
        //mValues = items;
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

        //holder.mItem = mValues.get(position);

        final int databaseId = mCursor.getInt(mCursor.getColumnIndex(ArtistColumns._ID));


        String artistImage = mCursor.getString(mCursor.getColumnIndex(ArtistColumns.SMALL));
        String artistName = mCursor.getString(mCursor.getColumnIndex(ArtistColumns.NAME));
        String artistGenres = mCursor.getString(mCursor.getColumnIndex(ArtistColumns.GENRES));

        Picasso.with(mArtistListActivity)
                .load(artistImage)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.mImageView);

        holder.mIdView.setText(artistName);
        holder.mContentView.setText(artistGenres);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ArtistDetailFragment.ARG_ITEM_ID, String.valueOf(databaseId)); // TODO: change String.valueOf()
                    ArtistDetailFragment fragment = new ArtistDetailFragment();
                    fragment.setArguments(arguments);
                    mArtistListActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.artist_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ArtistDetailActivity.class);
                    intent.putExtra(ArtistDetailFragment.ARG_ITEM_ID, databaseId);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //return mValues.size();

        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        //public DummyContent.DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mImageView = (ImageView) view.findViewById(R.id.image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
