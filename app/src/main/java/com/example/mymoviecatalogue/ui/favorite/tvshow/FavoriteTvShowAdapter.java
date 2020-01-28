package com.example.mymoviecatalogue.ui.favorite.tvshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.ui.tvshow.TvShowItems;

import java.util.ArrayList;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteTvShowViewHolder> {
    private final ArrayList<TvShowItems> listTvShows = new ArrayList<>();
    private FavoriteTvShowAdapter.OnItemClickCallback onItemClickCallback;

    public ArrayList<TvShowItems> getListTvShows() {
        return listTvShows;
    }

    public void setListTvShows(ArrayList<TvShowItems> listTvShows) {

        this.listTvShows.clear();

        this.listTvShows.addAll(listTvShows);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteTvShowAdapter.FavoriteTvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new FavoriteTvShowAdapter.FavoriteTvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteTvShowAdapter.FavoriteTvShowViewHolder holder, int position) {
        holder.tvTitle.setText(listTvShows.get(position).getName());
        holder.tvOverview.setText(listTvShows.get(position).getOverview());
        Glide.with(holder.itemView.getContext())
                .load(listTvShows.get(position).getPosterPath())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listTvShows.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvShows.size();
    }

    public void setOnItemClickCallback(FavoriteTvShowAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShowItems data);
    }

    class FavoriteTvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvOverview;

        FavoriteTvShowViewHolder(View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
        }
    }
}