package com.openclassrooms.mynews.Utils;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.openclassrooms.mynews.Controllers.Activities.DisplaySearchActivity;
import com.openclassrooms.mynews.Controllers.Activities.SearchActivity;
import com.openclassrooms.mynews.Models.NYTimesAPI;
import com.openclassrooms.mynews.Models.Response;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.content.Context.MODE_PRIVATE;

public class SearchAndNotifiyJob extends Job {

    private Disposable mDisposable;
    static final String TAG = "notification_job_tag";
    private Observable<NYTimesAPI> stream;
    private SharedPreferences prefs;
    private String mQuery;
    private String mNewsDesk;
    private String EXTRA_QUERY = "EXTRA_QUERY";
    private String EXTRA_NEWS_DESKS = "EXTRA_NEWS_DESKS";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {

        //prefs = getSharedPreferences("notification", MODE_PRIVATE);
        mQuery = "trump";
        mNewsDesk = "%22Politics%22";

        stream = NYTStreams.streamFetchSearchArticles(mQuery, mNewsDesk);

        this.mDisposable = stream
                .subscribeWith(new DisposableObserver<NYTimesAPI>(){
                    @Override
                    public void onNext(NYTimesAPI articles) {
                        Log.e("TopStoriesFragment", "On Next");
                        if (!articles.getResponse().getDocs().isEmpty())
                            sendNotification();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TopStoriesFragment", "On Error"+Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TopStoriesFragment", "On Complete");
                        if(mDisposable != null && mDisposable.isDisposed())
                            mDisposable.dispose();
                    }
                });

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

    private void sendNotification(){

            int notificationID = 1234;

            // Create an explicit intent for an Activity in your app
            Intent intent = new Intent(getContext(), DisplaySearchActivity.class);
            intent.putExtra(EXTRA_QUERY, "trump");
            intent.putExtra(EXTRA_NEWS_DESKS, "%22Politics%22");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

            NotificationHelper notificationHelper = new NotificationHelper(getContext());
            NotificationCompat.Builder builder = notificationHelper
                    .getNotificationBuilder("New New York Times Articles", "New articles correspond to your search", pendingIntent);
            notificationHelper.getNotificationManager().notify(notificationID, builder.build());
    }
}
