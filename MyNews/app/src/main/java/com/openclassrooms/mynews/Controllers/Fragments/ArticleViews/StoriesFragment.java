package com.openclassrooms.mynews.Controllers.Fragments.ArticleViews;


import android.support.v4.app.Fragment;
import com.openclassrooms.mynews.Models.NYTimesAPI;
import com.openclassrooms.mynews.Utils.NYTStreams;

import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoriesFragment extends BaseFragment {

    private static final Observable<NYTimesAPI> stream = NYTStreams.streamFetchTopStories();

    public StoriesFragment() { }

    public static StoriesFragment newInstance(){
        return(new StoriesFragment());
    }

    @Override
    protected Observable<NYTimesAPI> getStream() {
        return stream ;
    }
}
