package com.example.mymoviecatalogue.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.ui.movie.MovieAdapter;
import com.example.mymoviecatalogue.ui.movie.MovieDetailActivity;
import com.example.mymoviecatalogue.ui.movie.MovieItems;
import com.example.mymoviecatalogue.ui.movie.MovieViewModelSearch;
import com.example.mymoviecatalogue.ui.tvshow.TvShowAdapter;
import com.example.mymoviecatalogue.ui.tvshow.TvShowDetailActivity;
import com.example.mymoviecatalogue.ui.tvshow.TvShowItems;
import com.example.mymoviecatalogue.ui.tvshow.TvShowViewModelSearch;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    public static final String EXTRA_SEARCH = "extra_search";
    public static final String MOVIE_SEARCH = "movie_search";
    public static final String TV_SEARCH = "tv_search";
    private MovieAdapter movieAdapter;
    private TvShowAdapter tvShowAdapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MovieViewModelSearch movieViewModelSearch;
    private TvShowViewModelSearch tvShowViewModelSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        movieViewModelSearch = ViewModelProviders.of(this).get(MovieViewModelSearch.class);
        tvShowViewModelSearch = ViewModelProviders.of(this).get(TvShowViewModelSearch.class);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String query = getIntent().getStringExtra(EXTRA_SEARCH);
        SearchView searchView = findViewById(R.id.searchbox);
        final String setAction = getIntent().getAction();

        if(setAction!=null){
            if (setAction.equals(MOVIE_SEARCH)){
                movie();
                searchMovie(query);
            }else if(setAction.equals(TV_SEARCH)){
                tv();
                searchTv(query);
            }
        }

        searchView.setQuery(query, false);
        searchView.setIconified(false);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(setAction!=null) {
                    if (setAction.equals(MOVIE_SEARCH)) {
                        searchMovie(query);
                    } else if (setAction.equals(TV_SEARCH)) {
                        searchTv(query);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSelectedMovie(MovieItems movieItems) {
        Intent moveMovieDetail = new Intent(this, MovieDetailActivity.class);
        moveMovieDetail.putExtra(MovieDetailActivity.EXTRA_MOVIES, movieItems);
        startActivity(moveMovieDetail);
    }

    private void showSelectedTv(TvShowItems tvShowItems) {
        Intent moveTvShowDetail = new Intent(this, TvShowDetailActivity.class);
        moveTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_TV, tvShowItems);
        startActivity(moveTvShowDetail);
    }

    private void setActionBarTitle(int header) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(header);
        }
    }

    private void movie(){
        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(movieAdapter);

        int header = R.string.movie_search_result;
        setActionBarTitle(header);
    }

    private void tv(){
        tvShowAdapter = new TvShowAdapter();
        tvShowAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tvShowAdapter);

        int header = R.string.tv_search_result;
        setActionBarTitle(header);
    }

    private void searchMovie(String query){
        movieViewModelSearch.setMovie(query);
        showLoading(true);

        movieViewModelSearch.getMovies().observe(this, new Observer<ArrayList<MovieItems>>() {
            @Override
            public void onChanged(ArrayList<MovieItems> movieItems) {
                if (movieItems != null) {
                    movieAdapter.setData(movieItems);
                    showLoading(false);
                }
            }
        });

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieItems movieItems) {
                showSelectedMovie(movieItems);
            }
        });
    }

    private void searchTv(String query){
        tvShowViewModelSearch.setTvShow(query);
        showLoading(true);

        tvShowViewModelSearch.getTvShows().observe(this, new Observer<ArrayList<TvShowItems>>() {
            @Override
            public void onChanged(ArrayList<TvShowItems> tvShowItems) {
                if (tvShowItems != null) {
                    tvShowAdapter.setData(tvShowItems);
                    showLoading(false);
                }
            }
        });

        tvShowAdapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShowItems tvShowItems) {
                showSelectedTv(tvShowItems);
            }
        });
    }


}
