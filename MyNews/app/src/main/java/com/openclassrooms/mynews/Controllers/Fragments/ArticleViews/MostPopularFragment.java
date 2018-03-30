package com.openclassrooms.mynews.Controllers.Fragments.ArticleViews;


import android.support.v4.app.Fragment;

import com.openclassrooms.mynews.Models.NYTimesAPI;
import com.openclassrooms.mynews.Utils.NYTStreams;

import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostPopularFragment extends BaseFragment {

    @Override
    protected Observable<NYTimesAPI> getStream() {
        return NYTStreams.streamFetchMostPopular();
    }

    public MostPopularFragment() { }

    public static MostPopularFragment newInstance(){
        return(new MostPopularFragment());
    }


}
