package com.openclassrooms.mynews.Utils;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.openclassrooms.mynews.Controllers.Activities.DisplaySearchActivity;
import com.openclassrooms.mynews.Controllers.Activities.SearchActivity;
import com.openclassrooms.mynews.Models.NYTimesAPI;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SyncJob extends Job {

    public static final String TAG = "job_demo_tag";

    private Disposable mDisposable;
    private Observable<NYTimesAPI> stream;

    @Override
    @NonNull
    protected Result onRunJob(Params params) {

        this.mDisposable = stream
                .subscribeWith(new DisposableObserver<NYTimesAPI>(){
                    @Override
                    public void onNext(NYTimesAPI articles) {
                        Log.e("SyncJob", "On Next");
                        if(!articles.getResponse().getDocs().isEmpty()) {
                            int notificationID = 1234;

                            // Create an explicit intent for an Activity in your app
                            Intent intent = new Intent(getContext(), DisplaySearchActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

                            NotificationHelper notificationHelper = new NotificationHelper(getContext());
                            NotificationCompat.Builder builder = notificationHelper
                                    .getNotificationBuilder("Notif", "You have a new notification", pendingIntent);
                            notificationHelper.getNotificationManager().notify(notificationID, builder.build());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TopStoriesFragment", "On Error"+Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TopStoriesFragment", "On Complete");
                    }
                });


        return Result.SUCCESS;

    }

    public static void scheduleJob() {
        new JobRequest.Builder(SyncJob.TAG)
                //.setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                //.setUpdateCurrent(true)
                .startNow()
                .build()
                .schedule();
    }
}