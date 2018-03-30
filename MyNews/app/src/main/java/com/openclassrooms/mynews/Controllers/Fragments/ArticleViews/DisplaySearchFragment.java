package com.openclassrooms.mynews.Controllers.Fragments.ArticleViews;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.openclassrooms.mynews.Controllers.Activities.DetailActivity;
import com.openclassrooms.mynews.Models.NYTimesAPI;
import com.openclassrooms.mynews.Models.Response;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.ItemClickSupport;
import com.openclassrooms.mynews.Utils.NYTStreams;
import com.openclassrooms.mynews.Views.SearchArticleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplaySearchFragment extends BaseSearchFragment {

    public DisplaySearchFragment() { }

    protected Observable<NYTimesAPI> getStream() {
        Bundle args = getArguments();
        String mQuery = args.getString("query", "");
        String mNewsDesk = args.getString("newsDesk", "");
        int mBeginDate = args.getInt("beginDate", 0);
        int mEndDate = args.getInt("endDate", 0);

        return NYTStreams.streamFetchSearchArticles(mQuery, mNewsDesk, mBeginDate, mEndDate);
    }
}

