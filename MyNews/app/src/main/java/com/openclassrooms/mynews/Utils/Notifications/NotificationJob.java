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
import com.openclassrooms.mynews.Utils.SearchMgr;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationJob extends Job {

    private Disposable mDisposable;
    static final String TAG = "notification_job_tag";
    private Observable<NYTimesAPI> stream;

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {

        PersistableBundleCompat extras = params.getExtras();
        SearchMgr searchMgr = SearchMgr.getInstance();
        final Search search = searchMgr.getSearchFromPersistBundle(extras);

        stream = NYTStreams.streamFetchSearchArticles(search);

        this.mDisposable = stream
                .subscribeWith(new DisposableObserver<NYTimesAPI>(){
                    @Override
                    public void onNext(NYTimesAPI articles) {
                        Log.e("TopStoriesFragment", "On Next");
                        if (!articles.getResponse().getDocs().isEmpty())
                            sendNotification(search);
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

    public static void schedulePeriodic(Search search) {

        SearchMgr searchMgr = SearchMgr.getInstance();
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

        SearchMgr searchMgr = SearchMgr.getInstance();

            int notificationID = 1234;

            // Create an explicit intent for an Activity in your app
            Intent intent = new Intent(getContext(), DisplaySearchActivity.class);
            searchMgr.setSearchToIntent(intent, search);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationHelper notificationHelper = new NotificationHelper(getContext());
            NotificationCompat.Builder builder = notificationHelper
                    .getNotificationBuilder("New New York Times Articles", "New articles correspond to : " +search.getQuery(), pendingIntent);
            notificationHelper.getNotificationManager().notify(notificationID, builder.build());
    }
}
