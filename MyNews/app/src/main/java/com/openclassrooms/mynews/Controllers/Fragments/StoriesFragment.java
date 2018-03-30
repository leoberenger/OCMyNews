package com.openclassrooms.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.openclassrooms.mynews.Models.NYTimesAPI;
import com.openclassrooms.mynews.Utils.NYTStreams;

import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoriesFragment extends BaseFragment{

    public StoriesFragment() { }

    public static StoriesFragment newInstance(String storyType){
        StoriesFragment storiesFragment = new StoriesFragment();

        Bundle args = new Bundle();
        args.putString("storyType", storyType);
        storiesFragment.setArguments(args);

        return storiesFragment;
    }

    @Override
    protected Observable<NYTimesAPI> getStream() {

        Observable<NYTimesAPI> stream = null;

        Bundle args = getArguments();
        String storyType = args.getString("storyType");

        switch (storyType) {
            case "topStories":
                stream = NYTStreams.streamFetchTopStories();
                break;
            case "mostPopular":
                stream = NYTStreams.streamFetchMostPopular();
                break;
        }
        return stream;
    }
}
