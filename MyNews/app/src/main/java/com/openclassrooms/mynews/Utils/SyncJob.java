package com.openclassrooms.mynews.Utils;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.openclassrooms.mynews.Controllers.Activities.DisplaySearchActivity;

public class SyncJob extends Job {

    public static final String TAG = "job_demo_tag";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {

        int notificationID = 1234;

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(getContext(), DisplaySearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        NotificationHelper notificationHelper = new NotificationHelper(getContext());
        NotificationCompat.Builder builder = notificationHelper
                .getNotificationBuilder("Notif", "You have a new notification", pendingIntent);
        notificationHelper.getNotificationManager().notify(notificationID, builder.build());

        return Result.SUCCESS;
    }

    public static void scheduleJob() {
        new JobRequest.Builder(SyncJob.TAG)
                //.setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                //.setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                //.setRequirementsEnforced(true)
                //.setUpdateCurrent(true)
                .startNow()
                .build()
                .schedule();
    }
}