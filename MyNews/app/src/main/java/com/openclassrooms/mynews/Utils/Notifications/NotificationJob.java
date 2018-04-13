package com.openclassrooms.mynews.Utils.Notifications;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.openclassrooms.mynews.Controllers.Activities.DisplaySearchActivity;
import com.openclassrooms.mynews.Controllers.Activities.MainActivity;
import com.openclassrooms.mynews.Models.API.NYTimesAPI;
import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.Utils.APIRequests.NYTStreams;
import com.openclassrooms.mynews.Utils.DateMgr;
import com.openclassrooms.mynews.Utils.SearchMgr;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationJob extends Job {

    private Disposable mDisposable;
    static final String TAG = "notification_job_tag";
    private Observable<NYTimesAPI> stream;
    private final static SearchMgr searchMgr = SearchMgr.getInstance();
    private static final int notificationID = 1234;
    private int lastReadPubDate;
    private int nextPubDate;
    private DateMgr dateMgr = DateMgr.getInstance();

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {

        PersistableBundleCompat extras = params.getExtras();
        final Search search = searchMgr.getSearchFromPersistBundle(extras);

       //Set Begin Date = YESTERDAY and End Date = TODAY
        int yesterday = dateMgr.getDate(1);
        int today = dateMgr.getDate(0);

        search.setBeginDate(yesterday);
        search.setEndDate(today);
        stream = NYTStreams.streamFetchSearchArticles(search);
        executeHttpRequestToSeeIfNewArticles(stream, search);

        return Result.SUCCESS;
    }

    public static void schedulePeriodic(Search search) {

        PersistableBundleCompat bundleCompat = new PersistableBundleCompat();
        searchMgr.setSearchToPersistBundle(bundleCompat, search);

        new JobRequest.Builder(NotificationJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setExtras(bundleCompat)
                .build()
                .schedule();
    }

    public static void startNow(Search search) {

        PersistableBundleCompat bundleCompat = new PersistableBundleCompat();
        searchMgr.setSearchToPersistBundle(bundleCompat, search);

        new JobRequest.Builder(NotificationJob.TAG)
                .setExtras(bundleCompat)
                .startNow()
                .build()
                .schedule();
    }

    private void executeHttpRequestToSeeIfNewArticles(Observable<NYTimesAPI> stream, final Search search){
        this.mDisposable = stream
                .subscribeWith(new DisposableObserver<NYTimesAPI>(){
                    @Override
                    public void onNext(NYTimesAPI articles) {
                        Log.e("NotificationJob", "On Next");

                        if(!articles.getResponse().getDocs().isEmpty()){
                            nextPubDate = dateMgr.transformPublishedDate(articles.getResponse().getDocs().get(0).getPubDate());

                            Log.e("NotifJob", "lastreadPubDatePRE = " + lastReadPubDate + ", nextPubDate = " + nextPubDate);
                            if (nextPubDate > lastReadPubDate) {
                                sendNotification(search, lastReadPubDate);
                                lastReadPubDate = nextPubDate;
                            }else{
                                sendEmptyNotification("no new articles");
                            }
                        }else{
                            sendEmptyNotification("no articles");
                        }

                        Log.e("NotifJobPOST", "lastReadPubDate = "+ lastReadPubDate);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("NotificationJob", "On Error"+Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("NotificationJob", "On Complete");
                        if(mDisposable != null && mDisposable.isDisposed())
                            mDisposable.dispose();
                    }
                });
    }

    private void sendNotification(Search search, int lastReadPubDate){

        Log.e("send Notif", "lPB = " + lastReadPubDate);

        // Create intent for DisplaySearchActivity
        Intent intent = new Intent(getContext(), DisplaySearchActivity.class);
        searchMgr.setSearchToIntent(intent, search);
        intent.putExtra("lastReadPubDate", lastReadPubDate);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                        getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Create Notification
        NotificationHelper notificationHelper =
                new NotificationHelper(getContext());
        NotificationCompat.Builder builder =
                notificationHelper.getNotificationBuilder(
                        "New Articles",
                        "New articles corresponding to your query : " +search.getQuery(),
                        pendingIntent);
        notificationHelper.getNotificationManager().notify(notificationID, builder.build());


    }

    private void sendEmptyNotification(String s){

        // Create intent for DisplaySearchActivity
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Create Notification
        NotificationHelper notificationHelper =
                new NotificationHelper(getContext());
        NotificationCompat.Builder builder =
                notificationHelper.getNotificationBuilder(
                        ""+ s,
                        "No New articles",
                        pendingIntent);
        notificationHelper.getNotificationManager().notify(notificationID, builder.build());
    }
}
