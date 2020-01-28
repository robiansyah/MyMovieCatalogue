package com.example.mymoviecatalogue.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mymoviecatalogue.R;

public class ReminderActivity extends AppCompatActivity {

    private Switch releaseSwitch, dailySwitch;
    private DailyReceiver dailyReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        releaseSwitch = findViewById(R.id.switch_release);
        dailySwitch = findViewById(R.id.switch_daily);

        dailyReceiver = new DailyReceiver();

        releaseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(ReminderActivity.this, "Release Checked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReminderActivity.this, "Release Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String repeatTime = "17:26";
                    String repeatMessage = "Catalogue Movie missing you";
                    dailyReceiver.setRepeatingAlarm(ReminderActivity.this,
                            repeatTime, repeatMessage);
                } else {
                    dailyReceiver.cancelAlarm(ReminderActivity.this);
                }
            }
        });

    }
}
