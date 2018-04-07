package com.openclassrooms.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.openclassrooms.mynews.Models.NYTimesAPI;
import com.openclassrooms.mynews.Utils.NYTStreams;
import com.openclassrooms.mynews.Controllers.base.BaseDisplayFragment;

import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayStoriesFragment extends BaseDisplayFragment {

    private String STORY_TYPE_KEY = "storyType";
    private String TOP_STORIES_KEY = "topStories";
    private String MOST_POPULAR_KEY = "mostPopular";

    public DisplayStoriesFragment() { }


    @Override
    protected Observable<NYTimesAPI> getStream() {

        Bundle args = getArguments();
        String storyType = args.getString(STORY_TYPE_KEY);

        Observable<NYTimesAPI> stream = (storyType.equals(TOP_STORIES_KEY))?
                NYTStreams.streamFetchTopStories() :
                NYTStreams.streamFetchMostPopular() ;

        return stream;
    }
}
