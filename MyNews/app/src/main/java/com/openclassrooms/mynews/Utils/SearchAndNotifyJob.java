package com.openclassrooms.mynews.Utils;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.openclassrooms.mynews.Controllers.Activities.DisplaySearchActivity;
import com.openclassrooms.mynews.Models.NYTimesAPI;
import com.openclassrooms.mynews.Models.Search;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchAndNotifyJob extends Job {

    private Disposable mDisposable;
    static final String TAG = "notification_job_tag";
    private Observable<NYTimesAPI> stream;
    private SharedPreferences prefs;
    private String myQuery;
    private String myNewsDesk;

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {

        PersistableBundleCompat extras = params.getExtras();
        Search search = new Search();

        myQuery = search.getQuery(extras);
        myNewsDesk = search.getNewsDesk(extras);

        stream = NYTStreams.streamFetchSearchArticles(myQuery, myNewsDesk);

        this.mDisposable = stream
                .subscribeWith(new DisposableObserver<NYTimesAPI>(){
                    @Override
                    public void onNext(NYTimesAPI articles) {
                        Log.e("TopStoriesFragment", "On Next");
                        if (!articles.getResponse().getDocs().isEmpty())
                            sendNotification(myQuery, myNewsDesk);
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

    public static void schedulePeriodic(String query, String newsDesk) {

        Search search = new Search();
        PersistableBundleCompat bundleCompat = new PersistableBundleCompat();
        search.setQuery(bundleCompat, query);
        search.setNewsDesk(bundleCompat, newsDesk);

        //ADD CURRENT TIME
        // FOR BEGIN
        // AND END
        // DATE

        new JobRequest.Builder(SearchAndNotifyJob.TAG)
                //.setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                //.setUpdateCurrent(true)
                //.setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                //.setRequirementsEnforced(true)
                .setExtras(bundleCompat)
                .startNow()
                .build()
                .schedule();
    }

    private void sendNotification(String q, String nd){

            int notificationID = 1234;
            Search search = new Search();

            // Create an explicit intent for an Activity in your app
            Intent intent = new Intent(getContext(), DisplaySearchActivity.class);
            search.setSearch(intent, q, nd, 20180406, 20180406);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationHelper notificationHelper = new NotificationHelper(getContext());
            NotificationCompat.Builder builder = notificationHelper
                    .getNotificationBuilder("New New York Times Articles", "New articles correspond to : " +myQuery, pendingIntent);
            notificationHelper.getNotificationManager().notify(notificationID, builder.build());
    }
}
