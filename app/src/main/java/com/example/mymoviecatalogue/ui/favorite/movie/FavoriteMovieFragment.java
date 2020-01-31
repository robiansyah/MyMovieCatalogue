package com.example.mymoviecatalogue.ui.favorite.movie;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.db.DatabaseContract;
import com.example.mymoviecatalogue.db.MovieHelper;
import com.example.mymoviecatalogue.ui.movie.MovieDetailActivity;
import com.example.mymoviecatalogue.ui.movie.MovieItems;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

interface LoadMoviesCallback {
    void preExecute();

    void postExecute(ArrayList<MovieItems> movies);
}

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements LoadMoviesCallback {


    private static final String EXTRA_STATE = "EXTRA_STATE";
    RecyclerView recyclerView;
    private FavoriteMovieAdapter adapter;
    private ProgressBar progressBar;
    private MovieHelper movieHelper;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        progressBar = root.findViewById(R.id.progressBar);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        adapter = new FavoriteMovieAdapter();
        recyclerView.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, getActivity());
        getActivity().getContentResolver().registerContentObserver(DatabaseContract.MovieColumns.CONTENT_URI_MOVIE, true, myObserver);

        if (savedInstanceState == null) {
            new LoadMoviesAsync(getActivity(), this).execute();
        } else {
            ArrayList<MovieItems> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListMovies(list);
            }
        }

        adapter.setOnItemClickCallback(new FavoriteMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieItems data) {
                showSelected(data);
            }
        });
        return root;
    }

    public void onResume(){
        super.onResume();
        new FavoriteMovieFragment.LoadMoviesAsync(getActivity(), this).execute();
    }

    private void showSelected(MovieItems tvshow) {
        Intent moveDetailMovie = new Intent(getActivity(), MovieDetailActivity.class);
        moveDetailMovie.putExtra(MovieDetailActivity.EXTRA_MOVIES, tvshow);
        startActivity(moveDetailMovie);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovies());
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
    public void postExecute(ArrayList<MovieItems> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movies.size() > 0) {
            adapter.setListMovies(movies);
        } else {
            adapter.setListMovies(new ArrayList<MovieItems>());
            showSnackbarMessage(getString(R.string.favorite_movie_notif_no));
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<MovieItems>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private LoadMoviesAsync(Context context, LoadMoviesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MovieItems> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseContract.MovieColumns.CONTENT_URI_MOVIE, null, null, null, null);
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItems> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);

        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

//        @Override
//        public void onChange(boolean selfChange) {
//            super.onChange(selfChange);
//            new LoadMoviesAsync(context, (LoadMoviesCallback) context).execute();
//        }
    }
}
