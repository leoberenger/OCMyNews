package com.openclassrooms.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.MyAlarmReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    private PendingIntent mPendingIntent;

    @BindView(R.id.activity_notification_switch)
    Switch notificationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        this.configureNotificationSwitch();
        this.configureAlarmManager();
    }

    private void configureNotificationSwitch(){

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startAlarm();
                } else {
                    stopAlarm();
                }
            }
        });
    }

    private void configureAlarmManager(){
        Intent alarmIntent = new Intent(NotificationActivity.this, MyAlarmReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 0, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void startAlarm(){
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, mPendingIntent);
        Toast.makeText(this, "Alarm set!", Toast.LENGTH_LONG).show();
    }

    private void stopAlarm(){
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(mPendingIntent);
        Toast.makeText(this, "Alarm canceled", Toast.LENGTH_LONG).show();
    }
}

