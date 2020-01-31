package com.example.mymoviecatalogue.ui.favorite;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.ui.favorite.movie.FavoriteMovieFragment;
import com.example.mymoviecatalogue.ui.favorite.tvshow.FavoriteTvShowFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.title_movie,
            R.string.title_tv_show
    };

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoriteMovieFragment();
                break;
            case 1:
                fragment = new FavoriteTvShowFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
