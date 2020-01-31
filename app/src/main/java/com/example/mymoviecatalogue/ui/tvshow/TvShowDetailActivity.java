package com.example.mymoviecatalogue.ui.tvshow;

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
import com.example.mymoviecatalogue.ui.favorite.tvshow.MappingHelper;

import static com.example.mymoviecatalogue.db.DatabaseContract.TvColumns.CONTENT_URI_TV;
import static com.example.mymoviecatalogue.db.DatabaseContract.TvColumns.FIRSTAIRDATE;
import static com.example.mymoviecatalogue.db.DatabaseContract.TvColumns.IDAPI;
import static com.example.mymoviecatalogue.db.DatabaseContract.TvColumns.NAME;
import static com.example.mymoviecatalogue.db.DatabaseContract.TvColumns.OVERVIEW;
import static com.example.mymoviecatalogue.db.DatabaseContract.TvColumns.POPULARITY;
import static com.example.mymoviecatalogue.db.DatabaseContract.TvColumns.POSTERPATH;
import static com.example.mymoviecatalogue.db.DatabaseContract.TvColumns.VOTEAVERAGE;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TV = "extra_tv";
    private TvShowItems tvShow;
    private Uri uriWithId, uriWithIdApi;

    private String name;
    private String popularity;
    private String vote;
    private String firstAirDate;
    private String overview;
    private String poster;
    private int id;
    private int idApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        int header = R.string.tv_show_detail_header;
        setActionBarName(header);

        TextView tvName = findViewById(R.id.txt_name);
        TextView tvPopularity = findViewById(R.id.txt_popularity);
        TextView tvVote = findViewById(R.id.txt_vote);
        TextView tvFirstAirDate = findViewById(R.id.txt_release);
        TextView tvOverview = findViewById(R.id.txt_overview);
        ImageView ivPoster = findViewById(R.id.img_poster);

        tvShow = getIntent().getParcelableExtra(EXTRA_TV);
        id = tvShow.getId();
        idApi = tvShow.getIdApi();
        name = tvShow.getName();
        popularity = tvShow.getPopularity();
        vote = tvShow.getVoteAverage();
        firstAirDate = tvShow.getFirstAirDate();
        overview = tvShow.getOverview();
        poster = tvShow.getPosterPath();

        Glide.with(this)
                .load(poster)
                .into(ivPoster);
        tvName.setText(name);
        tvVote.setText(vote);
        tvPopularity.setText(popularity);
        tvFirstAirDate.setText(firstAirDate);
        tvOverview.setText(overview);
    }

    private void setActionBarName(int header) {
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
        uriWithIdApi = Uri.parse(CONTENT_URI_TV + "/" + idApi);
        Cursor cursor = getContentResolver().query(uriWithIdApi, null, null, null, null);
        if (cursor.moveToFirst()) {
            tvShow = MappingHelper.mapCursorToObject(cursor);
            uriWithId = Uri.parse(CONTENT_URI_TV + "/" + tvShow.getId());
            long result = getContentResolver().delete(uriWithId, null, null);
            if (result > 0)
                Toast.makeText(TvShowDetailActivity.this, R.string.favorite_notif_del_ok, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(TvShowDetailActivity.this, R.string.favorite_notif_del_no, Toast.LENGTH_SHORT).show();
        }else{
            tvShow.setIdApi(idApi);
            tvShow.setName(name);
            tvShow.setOverview(overview);
            tvShow.setPopularity(popularity);
            tvShow.setPosterPath(poster);
            tvShow.setFirstAirDate(firstAirDate);
            tvShow.setVoteAverage(vote);

            ContentValues values = new ContentValues();
            values.put(IDAPI, idApi);
            values.put(NAME, name);
            values.put(OVERVIEW, overview);
            values.put(POPULARITY, popularity);
            values.put(POSTERPATH, poster);
            values.put(FIRSTAIRDATE, firstAirDate);
            values.put(VOTEAVERAGE, vote);
            getContentResolver().insert(CONTENT_URI_TV, values);
            Toast.makeText(TvShowDetailActivity.this, R.string.favorite_notif_add_ok, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
