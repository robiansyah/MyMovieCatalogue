package com.example.mymoviecatalogue.ui.favorite.movie;

import android.database.Cursor;

import com.example.mymoviecatalogue.db.DatabaseContract;
import com.example.mymoviecatalogue.ui.movie.MovieItems;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<MovieItems> mapCursorToArrayList(Cursor moviesCursor) {
        ArrayList<MovieItems> movieList = new ArrayList<>();

        while (moviesCursor.moveToNext()) {
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID));
            int idApi = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.IDAPI));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE));
            String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW));
            String poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTERPATH));
            String vote = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTEAVERAGE));
            String release = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASEDATE));
            String popularity = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POPULARITY));
            movieList.add(new MovieItems(id, idApi, title, overview, poster, vote, release, popularity));
        }

        return movieList;
    }

    public static MovieItems mapCursorToObject(Cursor moviesCursor) {
        moviesCursor.moveToFirst();
        int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID));
        int idApi = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.IDAPI));
        String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE));
        String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW));
        String poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTERPATH));
        String vote = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTEAVERAGE));
        String release = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASEDATE));
        String popularity = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POPULARITY));
        return new MovieItems(id, idApi, title, overview, poster, vote, release, popularity);
    }
}
