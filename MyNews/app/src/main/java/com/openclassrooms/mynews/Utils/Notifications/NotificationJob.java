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
import com.openclassrooms.mynews.Models.API.NYTimesAPI;
import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.Utils.APIRequests.NYTStreams;
import com.openclassrooms.mynews.Utils.DateMgr;
import com.openclassrooms.mynews.Utils.SearchMgr;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationJob extends Job {

    private Disposable mDisposable;
    static final String TAG = "notification_job_tag";
    private Observable<NYTimesAPI> stream;
    private final static SearchMgr searchMgr = SearchMgr.getInstance();
    private final static DateMgr dateMgr = DateMgr.getInstance();
    private static final int notificationID = 1234;

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {

        PersistableBundleCompat extras = params.getExtras();
        final Search search = searchMgr.getSearchFromPersistBundle(extras);

        //Set Begin Date = YESTERDAY and End Date = TODAY
        int beginDate = dateMgr.getDate(1);
        int endDate = dateMgr.getDate(0);
        search.setBeginDate(beginDate);
        search.setEndDate(endDate);

        stream = NYTStreams.streamFetchSearchArticles(search);
        executeHttpRequest(stream, search);

        return Result.SUCCESS;
    }

    public static void schedulePeriodic(Search search) {

        PersistableBundleCompat bundleCompat = new PersistableBundleCompat();
        searchMgr.setSearchToPersistBundle(bundleCompat, search);

        new JobRequest.Builder(NotificationJob.TAG)
                //.setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                //.setUpdateCurrent(true)
                //.setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                //.setRequirementsEnforced(true)
                .setExtras(bundleCompat)
                .startNow()
                .build()
                .schedule();
    }

    private void sendNotification(Search search){

        // Create intent for DisplaySearchActivity
        Intent intent = new Intent(getContext(), DisplaySearchActivity.class);
        searchMgr.setSearchToIntent(intent, search);
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

    private void executeHttpRequest(Observable<NYTimesAPI> stream, final Search search){
        this.mDisposable = stream
                .subscribeWith(new DisposableObserver<NYTimesAPI>(){
                    @Override
                    public void onNext(NYTimesAPI articles) {
                        Log.e("NotificationJob", "On Next");

                        if (!articles.getResponse().getDocs().isEmpty())
                            sendNotification(search);
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
}
