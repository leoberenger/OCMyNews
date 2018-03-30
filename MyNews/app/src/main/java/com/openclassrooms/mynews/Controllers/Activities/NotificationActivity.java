package com.openclassrooms.mynews.Controllers.Activities;

import android.app.PendingIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.evernote.android.job.JobManager;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.JobCreator;
import com.openclassrooms.mynews.Utils.SyncJob;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.activity_notification_switch)
    Switch notificationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        this.configureNotificationSwitch();
    }

    private void configureNotificationSwitch(){

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    JobManager.create(getApplicationContext()).addJobCreator(new JobCreator());
                    SyncJob.scheduleJob();
                } else {
                    //stopAlarm();
                }
            }
        });
    }
}

