package com.example.mymoviecatalogue.ui.movie;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.db.DatabaseContract;
import com.example.mymoviecatalogue.db.MovieHelper;

import static com.example.mymoviecatalogue.db.DatabaseContract.MovieColumns.IDAPI;
import static com.example.mymoviecatalogue.db.DatabaseContract.MovieColumns.TITLE;
import static com.example.mymoviecatalogue.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.example.mymoviecatalogue.db.DatabaseContract.MovieColumns.POPULARITY;
import static com.example.mymoviecatalogue.db.DatabaseContract.MovieColumns.POSTERPATH;
import static com.example.mymoviecatalogue.db.DatabaseContract.MovieColumns.RELEASEDATE;
import static com.example.mymoviecatalogue.db.DatabaseContract.MovieColumns.VOTEAVERAGE;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIES = "extra_movies";
    private MovieHelper movieHelper;
    private MovieItems movie;

    private String title;
    private String popularity;
    private String vote;
    private String release;
    private String overview;
    private String poster;
    private int id;
    private int idApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        int header = R.string.movies_detail_header;
        setActionBarTitle(header);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        TextView tvTitle = findViewById(R.id.txt_title);
        TextView tvPopularity = findViewById(R.id.txt_popularity);
        TextView tvVote = findViewById(R.id.txt_vote);
        TextView tvRelease = findViewById(R.id.txt_release);
        TextView tvOverview = findViewById(R.id.txt_overview);
        ImageView ivPoster = findViewById(R.id.img_poster);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIES);
        id = movie.getId();
        idApi = movie.getIdApi();
        title = movie.getTitle();
        popularity = movie.getPopularity();
        vote = movie.getVoteAverage();
        release = movie.getReleaseDate();
        overview = movie.getOverview();
        poster = movie.getPosterPath();


        Glide.with(this)
                .load(poster)
                .into(ivPoster);
        tvTitle.setText(title);
        tvVote.setText(vote);
        tvPopularity.setText(popularity);
        tvRelease.setText(release);
        tvOverview.setText(overview);
    }

    private void setActionBarTitle(int header) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(header);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fav_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                add_fav();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void add_fav(){
        Cursor cursor = movieHelper.queryById(String.valueOf(idApi));
        try {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID));
                long result = movieHelper.deleteById(String.valueOf(id));
                if (result > 0)
                    Toast.makeText(MovieDetailActivity.this, R.string.favorite_notif_del_ok, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MovieDetailActivity.this, R.string.favorite_notif_del_no, Toast.LENGTH_SHORT).show();
            }else{
                movie.setIdApi(idApi);
                movie.setTitle(title);
                movie.setOverview(overview);
                movie.setPopularity(popularity);
                movie.setPosterPath(poster);
                movie.setReleaseDate(release);
                movie.setVoteAverage(vote);

                ContentValues values = new ContentValues();
                values.put(IDAPI, idApi);
                values.put(TITLE, title);
                values.put(OVERVIEW, overview);
                values.put(POPULARITY, popularity);
                values.put(POSTERPATH, poster);
                values.put(RELEASEDATE, release);
                values.put(VOTEAVERAGE, vote);
                long result = movieHelper.insert(values);
                if(result>0)
                    Toast.makeText(MovieDetailActivity.this, R.string.favorite_notif_add_ok, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MovieDetailActivity.this, R.string.favorite_notif_add_no, Toast.LENGTH_SHORT).show();
            }
        } finally {
            cursor.close();
        }

    }

    @Override
    public void onClick(View v) {

    }
}
