package com.example.mymoviecatalogue.preference;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.reminder.DailyReceiver;
import com.example.mymoviecatalogue.reminder.ReleaseReceiver;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String DAILY;
    private String RELEASE;

    private SwitchPreference dailyPreference;
    private SwitchPreference releasePreference;

    private DailyReceiver dailyReceiver;
    private ReleaseReceiver releaseReceiver;



    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        init();
        setSummaries();
    }

    private void init() {
        RELEASE = getResources().getString(R.string.key_release);
        DAILY = getResources().getString(R.string.key_daily);

        releasePreference = findPreference(RELEASE);
        dailyPreference = findPreference(DAILY);

        releaseReceiver = new ReleaseReceiver();
        dailyReceiver = new DailyReceiver();
    }

    private void setSummaries() {
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        releasePreference.setChecked(sh.getBoolean(RELEASE, false));
        dailyPreference.setChecked(sh.getBoolean(DAILY, false));
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
        if (key.equals(RELEASE)) {
            boolean isChecked = sharedPreferences.getBoolean(RELEASE, false);
            releasePreference.setChecked(isChecked);
            if(isChecked==true){
                releaseReceiver.setRepeatingAlarm(getActivity());
            }else{
                releaseReceiver.cancelAlarm(getActivity());
            }
        }

        if (key.equals(DAILY)) {
            boolean isChecked = sharedPreferences.getBoolean(DAILY, false);
            dailyPreference.setChecked(isChecked);
            if(isChecked==true){
                dailyReceiver.setRepeatingAlarm(getActivity());
            }else{
                dailyReceiver.cancelAlarm(getActivity());
            }
        }
    }
}
