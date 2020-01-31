package com.example.mymoviecatalogue.ui.tvshow;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviecatalogue.BuildConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {

    private static final String API_KEY = BuildConfig.API_KEY;
    private MutableLiveData<ArrayList<TvShowItems>> listTvShows = new MutableLiveData<>();

    void setTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShowItems> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        final String img_url = "https://image.tmdb.org/t/p/w185";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < 15; i++) {
                        JSONObject tvShow = list.getJSONObject(i);
                        TvShowItems tvShowItems = new TvShowItems();
                        tvShowItems.setIdApi(tvShow.getInt("id"));
                        tvShowItems.setName(tvShow.getString("name"));
                        tvShowItems.setPosterPath(img_url + tvShow.getString("poster_path"));
                        tvShowItems.setOverview(tvShow.getString("overview"));
                        tvShowItems.setVoteAverage(tvShow.getString("vote_average"));
                        tvShowItems.setFirstAirDate(tvShow.getString("first_air_date"));
                        tvShowItems.setPopularity(tvShow.getString("popularity"));
                        listItems.add(tvShowItems);
                    }
                    listTvShows.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    LiveData<ArrayList<TvShowItems>> getTvShows() {
        return listTvShows;
    }
}