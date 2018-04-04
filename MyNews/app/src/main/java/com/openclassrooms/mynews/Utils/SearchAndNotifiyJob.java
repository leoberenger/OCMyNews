package com.openclassrooms.mynews.Utils;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.openclassrooms.mynews.Controllers.Activities.DisplaySearchActivity;

import java.util.concurrent.TimeUnit;

public class SearchAndNotifiyJob extends Job {

    static final String TAG = "notification_job_tag";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {

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

    public static void schedulePeriodic() {
        new JobRequest.Builder(SearchAndNotifiyJob.TAG)
                //.setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                //.setUpdateCurrent(true)
                //.setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                //.setRequirementsEnforced(true)
                .startNow()
                .build()
                .schedule();
    }
}
