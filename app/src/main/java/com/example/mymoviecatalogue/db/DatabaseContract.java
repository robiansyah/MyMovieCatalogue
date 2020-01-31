package com.example.mymoviecatalogue.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String TABLE_MOVIE = "movie";
    public static final String TABLE_TVSHOW = "tvshow";

    public static final String AUTHORITY = "com.example.mymoviecatalogue";
    private static final String SCHEME = "content";

    private DatabaseContract(){}

    public static final class MovieColumns implements BaseColumns {
        public static String IDAPI = "idApi";
        public static String TITLE = "title";
        public static String VOTEAVERAGE = "voteAverage";
        public static String OVERVIEW = "overview";
        public static String RELEASEDATE = "releaseDate";
        public static String POSTERPATH = "posterPath";
        public static String POPULARITY = "popularity";

        public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    public static final class TvColumns implements BaseColumns {
        public static String IDAPI = "idApi";
        public static String NAME = "name";
        public static String VOTEAVERAGE = "voteAverage";
        public static String OVERVIEW = "overview";
        public static String FIRSTAIRDATE = "firstAirDate";
        public static String POSTERPATH = "posterPath";
        public static String POPULARITY = "popularity";

        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TVSHOW)
                .build();
    }

}
