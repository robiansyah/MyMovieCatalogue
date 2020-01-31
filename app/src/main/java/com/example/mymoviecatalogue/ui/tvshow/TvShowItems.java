package com.example.mymoviecatalogue.ui.tvshow;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShowItems implements Parcelable {

    public static final Creator<TvShowItems> CREATOR = new Creator<TvShowItems>() {
        @Override
        public TvShowItems createFromParcel(Parcel in) {
            return new TvShowItems(in);
        }

        @Override
        public TvShowItems[] newArray(int size) {
            return new TvShowItems[size];
        }
    };
    private int id;
    private int idApi;
    private String name;
    private String voteAverage;
    private String overview;
    private String firstAirDate;
    private String posterPath;
    private String popularity;

    protected TvShowItems(Parcel in) {
        id = in.readInt();
        idApi = in.readInt();
        name = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
        firstAirDate = in.readString();
        posterPath = in.readString();
        popularity = in.readString();
    }

    public TvShowItems() {

    }

    public TvShowItems(int id, int idApi, String name, String overview, String poster, String vote, String release, String popularity) {
        this.id = id;
        this.idApi = idApi;
        this.name = name;
        this.overview = overview;
        this.posterPath = poster;
        this.voteAverage = vote;
        this.firstAirDate = release;
        this.popularity = popularity;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
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
        dest.writeString(name);
        dest.writeString(voteAverage);
        dest.writeString(overview);
        dest.writeString(firstAirDate);
        dest.writeString(posterPath);
        dest.writeString(popularity);
    }
}
