package com.example.mymoviecatalogue.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.preference.MyPreferenceFragment;

public class ReminderActivity extends AppCompatActivity {

    private Switch releaseSwitch, dailySwitch;
    private DailyReceiver dailyReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        getSupportFragmentManager().beginTransaction().add(R.id.preference, new MyPreferenceFragment()).commit();

    }
}
