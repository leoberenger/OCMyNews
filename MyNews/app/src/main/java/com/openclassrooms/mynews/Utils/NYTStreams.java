package com.openclassrooms.mynews.Utils;


import com.openclassrooms.mynews.Models.NYTimesAPI;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by berenger on 02/03/2018.
 */

public class NYTStreams {
    // TOP STORIES
    public static Observable<NYTimesAPI> streamFetchTopStories(){
        NYTService nYTService = NYTService.retrofit.create(NYTService.class);
        return nYTService.getTopStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // MOST POPULAR
    public static Observable<NYTimesAPI> streamFetchMostPopular(){
        NYTService nYTService = NYTService.retrofit.create(NYTService.class);
        return nYTService.getMostPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    //SEARCH ARTICLE
    public static Observable<NYTimesAPI> streamFetchSearchArticles(String query, String desk, int startDate, int endDate){
        NYTService nYTService = NYTService.retrofit.create(NYTService.class);
        return nYTService.getSearchArticles(query, desk, startDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
