package com.openclassrooms.mynews.Utils;

import android.app.job.JobParameters;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import static android.content.Context.MODE_PRIVATE;

public class MyJobCreator implements com.evernote.android.job.JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {

        switch (tag) {
            case SearchAndNotifyJob.TAG:
                return new SearchAndNotifyJob();
            default:
                return null;
        }
    }
}
