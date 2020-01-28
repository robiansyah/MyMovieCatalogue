package com.example.mymoviecatalogue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mymoviecatalogue.db.DatabaseContract.MovieColumns;
import com.example.mymoviecatalogue.db.DatabaseContract.TvColumns;
import static com.example.mymoviecatalogue.db.DatabaseContract.TABLE_MOVIE;
import static com.example.mymoviecatalogue.db.DatabaseContract.TABLE_TVSHOW;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmovieapp";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_MOVIE,
            MovieColumns._ID,
            MovieColumns.IDAPI,
            MovieColumns.TITLE,
            MovieColumns.VOTEAVERAGE,
            MovieColumns.OVERVIEW,
            MovieColumns.RELEASEDATE,
            MovieColumns.POSTERPATH,
            MovieColumns.POPULARITY
    );

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_TVSHOW,
            TvColumns._ID,
            TvColumns.IDAPI,
            TvColumns.NAME,
            TvColumns.VOTEAVERAGE,
            TvColumns.OVERVIEW,
            TvColumns.FIRSTAIRDATE,
            TvColumns.POSTERPATH,
            TvColumns.POPULARITY
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("table", SQL_CREATE_TABLE_TVSHOW);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOW);
        onCreate(db);
    }
}