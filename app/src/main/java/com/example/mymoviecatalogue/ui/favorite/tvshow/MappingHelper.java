package com.example.mymoviecatalogue.ui.favorite.tvshow;

import android.database.Cursor;

import com.example.mymoviecatalogue.db.DatabaseContract;
import com.example.mymoviecatalogue.ui.tvshow.TvShowItems;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<TvShowItems> mapCursorToArrayList(Cursor tvCursor) {
        ArrayList<TvShowItems> tvList = new ArrayList<>();

        while (tvCursor.moveToNext()) {
            int id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns._ID));
            int idApi = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.IDAPI));
            String name = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.NAME));
            String overview = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.OVERVIEW));
            String poster = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.POSTERPATH));
            String vote = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.VOTEAVERAGE));
            String release = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.FIRSTAIRDATE));
            String popularity = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.POPULARITY));
            tvList.add(new TvShowItems(id, idApi, name, overview, poster, vote, release, popularity));
        }

        return tvList;
    }

    public static TvShowItems mapCursorToObject(Cursor tvCursor) {
        tvCursor.moveToFirst();
        int id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns._ID));
        int idApi = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.IDAPI));
        String name = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.NAME));
        String overview = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.OVERVIEW));
        String poster = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.POSTERPATH));
        String vote = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.VOTEAVERAGE));
        String release = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.FIRSTAIRDATE));
        String popularity = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.POPULARITY));
        return new TvShowItems(id, idApi, name, overview, poster, vote, release, popularity);
    }
}