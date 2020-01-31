package com.example.mymoviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.db.DatabaseContract;
import com.example.mymoviecatalogue.ui.movie.MovieItems;

import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private Cursor cursor;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(DatabaseContract.MovieColumns.CONTENT_URI_MOVIE, null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }

        final long identifyToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(DatabaseContract.MovieColumns.CONTENT_URI_MOVIE, null, null, null, null);
        Binder.restoreCallingIdentity(identifyToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        if (cursor != null) {
            return cursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
        MovieItems movieItems = getMoviePosition(position);
        String urlPhoto = movieItems.getPosterPath();
        try {
            Bitmap bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(urlPhoto)
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.imageView, bmp);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(ImageFavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private MovieItems getMoviePosition(int position) {
        if (cursor.moveToPosition(position)) {
            return new MovieItems(cursor);
        } else {
            throw new IllegalStateException("Error");
        }
    }


}
