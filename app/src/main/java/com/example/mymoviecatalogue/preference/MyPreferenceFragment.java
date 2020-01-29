package com.example.mymoviecatalogue.preference;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.mymoviecatalogue.R;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String DAILY;
    private String RELEASE;

    private SwitchPreference dailyPreference;
    private SwitchPreference releasePreference;



    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        init();
        setSummaries();
    }

    private void init() {
        DAILY = getResources().getString(R.string.key_daily);
        RELEASE = getResources().getString(R.string.key_release);

        dailyPreference = (SwitchPreference) findPreference(DAILY);
        releasePreference = (SwitchPreference) findPreference(RELEASE);
    }

    private void setSummaries() {
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        dailyPreference.setChecked(sh.getBoolean(DAILY, false));
        releasePreference.setChecked(sh.getBoolean(RELEASE, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(DAILY)) {
            dailyPreference.setChecked(sharedPreferences.getBoolean(DAILY, false));
        }

        if (key.equals(DAILY)) {
            dailyPreference.setChecked(sharedPreferences.getBoolean(DAILY, false));
        }
    }
}