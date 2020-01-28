package com.example.mymoviecatalogue.ui.tvshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviecatalogue.R;

import java.util.ArrayList;

public class TvShowFragment extends Fragment {

    private TvShowAdapter adapter;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TvShowViewModel tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tv_show, container, false);

        progressBar = root.findViewById(R.id.progressBar);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new TvShowAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        tvShowViewModel.setTvShow();
        showLoading(true);

        tvShowViewModel.getTvShows().observe(this, new Observer<ArrayList<TvShowItems>>() {
            @Override
            public void onChanged(ArrayList<TvShowItems> tvShowItems) {
                if (tvShowItems != null) {
                    adapter.setData(tvShowItems);
                    showLoading(false);
                }
            }
        });

        adapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShowItems tvShowItems) {
                showSelected(tvShowItems);
            }
        });

        return root;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSelected(TvShowItems tvShow) {
        Intent moveDetail = new Intent(getActivity(), TvShowDetailActivity.class);
        moveDetail.putExtra(TvShowDetailActivity.EXTRA_TV, tvShow);
        startActivity(moveDetail);
    }
}