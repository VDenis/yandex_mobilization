package com.denis.home.yandexmobilization.ui.artistList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denis.home.yandexmobilization.R;
import com.denis.home.yandexmobilization.dummy.DummyContent;
import com.denis.home.yandexmobilization.ui.artistDetail.ArtistDetailActivity;
import com.denis.home.yandexmobilization.ui.artistDetail.ArtistDetailFragment;

import java.util.List;

/**
 * Created by Denis on 20.04.2016.
 */
public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private ArtistListActivity mArtistListActivity;
    private final List<DummyContent.DummyItem> mValues;
    private final boolean mTwoPane;

    public SimpleItemRecyclerViewAdapter(ArtistListActivity artistListActivity, List<DummyContent.DummyItem> items, boolean twoPane) {
        mArtistListActivity = artistListActivity;
        mValues = items;
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
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ArtistDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                    ArtistDetailFragment fragment = new ArtistDetailFragment();
                    fragment.setArguments(arguments);
                    mArtistListActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.artist_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ArtistDetailActivity.class);
                    intent.putExtra(ArtistDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyContent.DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
