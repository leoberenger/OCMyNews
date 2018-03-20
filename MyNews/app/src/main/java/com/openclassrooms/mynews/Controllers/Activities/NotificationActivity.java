package com.openclassrooms.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openclassrooms.mynews.Controllers.Fragments.NotificationFragment;
import com.openclassrooms.mynews.R;

public class NotificationActivity extends AppCompatActivity {

    private NotificationFragment mNotificationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        this.configureAndShowSearchFragment();
    }


    private void configureAndShowSearchFragment() {
        if (mNotificationFragment == null) {
            mNotificationFragment = new NotificationFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_notification_frame_layout, mNotificationFragment)
                    .commit();
        }
    }
}

