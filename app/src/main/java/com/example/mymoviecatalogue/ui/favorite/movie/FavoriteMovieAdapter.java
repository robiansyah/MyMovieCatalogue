package com.example.mymoviecatalogue.ui.favorite.movie;

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
import com.example.mymoviecatalogue.ui.movie.MovieItems;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder> {
    private final ArrayList<MovieItems> listMovies = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public ArrayList<MovieItems> getListMovies() {
        return listMovies;
    }

    public void setListMovies(ArrayList<MovieItems> listMovies) {

        this.listMovies.clear();

        this.listMovies.addAll(listMovies);

        notifyDataSetChanged();
    }

    public FavoriteMovieAdapter(){

    }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new FavoriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteMovieViewHolder holder, int position) {
        holder.tvTitle.setText(listMovies.get(position).getTitle());
        holder.tvOverview.setText(listMovies.get(position).getOverview());
        Glide.with(holder.itemView.getContext())
                .load(listMovies.get(position).getPosterPath())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listMovies.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieItems data);
    }

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvOverview;

        FavoriteMovieViewHolder(View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
        }
    }
}
