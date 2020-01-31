package com.example.mymoviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import com.example.mymoviecatalogue.db.MovieHelper;
import com.example.mymoviecatalogue.db.TvHelper;

import static com.example.mymoviecatalogue.db.DatabaseContract.AUTHORITY;
import static com.example.mymoviecatalogue.db.DatabaseContract.TABLE_MOVIE;
import static com.example.mymoviecatalogue.db.DatabaseContract.TABLE_TVSHOW;
import static com.example.mymoviecatalogue.db.DatabaseContract.MovieColumns.CONTENT_URI_MOVIE;
import static com.example.mymoviecatalogue.db.DatabaseContract.TvColumns.CONTENT_URI_TV;

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TVSHOW = 3;
    private static final int TVSHOW_ID = 4;
    private MovieHelper movieHelper;
    private TvHelper tvHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY,
                TABLE_MOVIE + "/#",
                MOVIE_ID);
        sUriMatcher.addURI(AUTHORITY, TABLE_TVSHOW, TVSHOW);
        sUriMatcher.addURI(AUTHORITY,
                TABLE_TVSHOW + "/#",
                TVSHOW_ID);
    }

    public MovieProvider() {
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        tvHelper = TvHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                movieHelper.open();
                cursor = movieHelper.queryAll();
                break;
            case MOVIE_ID:
                movieHelper.open();
                cursor = movieHelper.queryById(uri.getLastPathSegment());
                break;
            case TVSHOW:
                tvHelper.open();
                cursor = tvHelper.queryAll();
                break;
            case TVSHOW_ID:
                tvHelper.open();
                cursor = tvHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri uri_add = null;
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                movieHelper.open();
                added = movieHelper.insert(contentValues);
                getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, null);
                uri_add = Uri.parse(CONTENT_URI_MOVIE + "/" + added);
                break;
            case TVSHOW:
                tvHelper.open();
                added = tvHelper.insert(contentValues);
                getContext().getContentResolver().notifyChange(CONTENT_URI_TV, null);
                Uri.parse(CONTENT_URI_TV + "/" + added);
                break;
            default:
                throw new SQLException("failed to insert row into " + uri);
        }

        return uri_add;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                movieHelper.open();
                deleted = movieHelper.deleteById(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, null);
                break;
            case TVSHOW_ID:
                tvHelper.open();
                deleted = tvHelper.deleteById(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(CONTENT_URI_TV, null);
                break;
            default:
                deleted = 0;
                break;
        }
        return deleted;
    }
}
