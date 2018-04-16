package com.openclassrooms.mynews.notifications;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.openclassrooms.mynews.controllers.activities.DisplaySearchActivity;
import com.openclassrooms.mynews.controllers.activities.MainActivity;
import com.openclassrooms.mynews.models.API.NYTimesAPI;
import com.openclassrooms.mynews.models.Search;
import com.openclassrooms.mynews.api.NYTStreams;
import com.openclassrooms.mynews.utils.DateMgr;
import com.openclassrooms.mynews.utils.SearchMgr;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.content.Context.MODE_PRIVATE;

public class NotificationJob extends Job {

    private Disposable mDisposable;
    static final String TAG = "notification_job_tag";
    private Observable<NYTimesAPI> stream;
    private final static SearchMgr searchMgr = SearchMgr.getInstance();
    private static final int notificationID = 1234;
    private int lastReadPubDate;
    private int nextPubDate;
    private DateMgr dateMgr = DateMgr.getInstance();
    private SharedPreferences prefs;

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

        prefs =  PreferenceManager.getDefaultSharedPreferences(getContext());
        lastReadPubDate = prefs.getInt("lastReadPubDate", 0);

        executeHttpRequestToSeeIfNewArticles(stream, search, lastReadPubDate);

        return Result.SUCCESS;
    }

    public static void schedulePeriodic(Search search) {

        PersistableBundleCompat bundleCompat = new PersistableBundleCompat();
        searchMgr.setSearchToPersistBundle(bundleCompat, search);

        new JobRequest.Builder(NotificationJob.TAG)
                .setPeriodic(TimeUnit.DAYS.toDays(1), TimeUnit.DAYS.toDays(1))
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

    private void executeHttpRequestToSeeIfNewArticles(Observable<NYTimesAPI> stream, final Search search, final int lastReadPubDate){
        this.mDisposable = stream
                .subscribeWith(new DisposableObserver<NYTimesAPI>(){
                    @Override
                    public void onNext(NYTimesAPI articles) {

                        if(!articles.getResponse().getDocs().isEmpty()){
                            nextPubDate = dateMgr.transformPublishedDate(articles.getResponse().getDocs().get(0).getPubDate());

                            if (nextPubDate > lastReadPubDate) {
                                sendNotification(search, lastReadPubDate);
                                prefs.edit().putInt("lastReadPubDate", nextPubDate).apply();
                            }
                        }
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
}
