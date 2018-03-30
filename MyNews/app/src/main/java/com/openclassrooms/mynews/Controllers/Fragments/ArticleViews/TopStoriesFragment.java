package com.openclassrooms.mynews.Controllers.Fragments.ArticleViews;


import android.support.v4.app.Fragment;
import com.openclassrooms.mynews.Models.NYTimesAPI;
import com.openclassrooms.mynews.Utils.NYTStreams;
import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends BaseFragment{

    public TopStoriesFragment() { }

    public static TopStoriesFragment newInstance(){
        return(new TopStoriesFragment());
    }

    @Override
    protected Observable<NYTimesAPI> getStream() {
        return NYTStreams.streamFetchTopStories();
    }
}
