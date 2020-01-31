package com.example.mymoviecatalogue.ui.movie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.mymoviecatalogue.db.DatabaseContract;

public class MovieItems implements Parcelable {
    private int id;
    private int idApi;
    private String title;
    private String voteAverage;
    private String overview;
    private String releaseDate;
    private String posterPath;
    private String popularity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdApi() {
        return idApi;
    }

    public void setIdApi(int idApi) {
        this.idApi = idApi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idApi);
        dest.writeString(title);
        dest.writeString(voteAverage);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeString(popularity);
    }

    public MovieItems(){

    }

    public MovieItems(Cursor cursor) {
        this.posterPath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTERPATH));
    }

    public MovieItems(int id, int idApi, String title, String overview, String poster, String vote, String release, String popularity) {
        this.id = id;
        this.idApi = idApi;
        this.title = title;
        this.overview = overview;
        this.posterPath = poster;
        this.voteAverage = vote;
        this.releaseDate = release;
        this.popularity = popularity;
    }

    public MovieItems(Parcel in) {
        id = in.readInt();
        idApi = in.readInt();
        title = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        popularity = in.readString();
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}
