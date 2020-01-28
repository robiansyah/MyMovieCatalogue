package com.example.mymoviecatalogue.ui.favorite.tvshow;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.db.TvHelper;
import com.example.mymoviecatalogue.ui.tvshow.TvShowDetailActivity;
import com.example.mymoviecatalogue.ui.tvshow.TvShowItems;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

interface LoadTvShowsCallback {
    void preExecute();

    void postExecute(ArrayList<TvShowItems> notes);
}

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment implements LoadTvShowsCallback {


    private static final String EXTRA_STATE = "EXTRA_STATE";
    RecyclerView recyclerView;
    private FavoriteTvShowAdapter adapter;
    private ProgressBar progressBar;
    private TvHelper tvHelper;

    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tv_show, container, false);
        progressBar = root.findViewById(R.id.progressBar);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        adapter = new FavoriteTvShowAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickCallback(new FavoriteTvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShowItems data) {
                showSelected(data);
            }
        });

        tvHelper = TvHelper.getInstance(getActivity().getApplicationContext());
        tvHelper.open();

        return root;
    }

    public void onResume(){
        super.onResume();
        new FavoriteTvShowFragment.LoadTvShowsAsync(tvHelper, this).execute();
    }

    private void showSelected(TvShowItems tvshow) {
        Intent moveDetailTvShow = new Intent(getActivity(), TvShowDetailActivity.class);
        moveDetailTvShow.putExtra(TvShowDetailActivity.EXTRA_TV, tvshow);
        startActivity(moveDetailTvShow);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListTvShows());
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<TvShowItems> notes) {
        progressBar.setVisibility(View.INVISIBLE);
        if (notes.size() > 0) {
            adapter.setListTvShows(notes);
        } else {
            adapter.setListTvShows(new ArrayList<TvShowItems>());
            showSnackbarMessage(getString(R.string.favorite_tv_notif_no));
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    private static class LoadTvShowsAsync extends AsyncTask<Void, Void, ArrayList<TvShowItems>> {

        private final WeakReference<TvHelper> weakTvHelper;
        private final WeakReference<LoadTvShowsCallback> weakCallback;

        private LoadTvShowsAsync(TvHelper tvHelper, LoadTvShowsCallback callback) {
            weakTvHelper = new WeakReference<>(tvHelper);
            weakCallback = new WeakReference<>(callback);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvShowItems> doInBackground(Void... voids) {
            Cursor dataCursor = weakTvHelper.get().queryAll();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<TvShowItems> notes) {
            super.onPostExecute(notes);

            weakCallback.get().postExecute(notes);

        }
    }
}
