package com.openclassrooms.mynews.Controllers.Fragments.ArticleViews;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicFragment extends BaseSearchFragment {


    public TopicFragment() { }

    public static TopicFragment newInstance(String newsDesk){
        TopicFragment topicFragment = new TopicFragment();

        Bundle args = new Bundle();
        args.putString("newsDesk", newsDesk);
        topicFragment.setArguments(args);

        return topicFragment;
    }

    @Override
    protected Observable<NYTimesAPI> getStream() {
        Bundle args = getArguments();
        String mNewsDesk = "news_desk:(%22" + args.getString("newsDesk", "") + "%22)";
        return NYTStreams.streamFetchTopic(mNewsDesk);
    }

}

