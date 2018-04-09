package com.openclassrooms.mynews.Utils.APIRequests;


import com.openclassrooms.mynews.Models.API.NYTimesAPI;
import com.openclassrooms.mynews.Models.Search;

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

    //TOPIC
    public static Observable<NYTimesAPI> streamFetchTopic(String desk){
        NYTService nYTService = NYTService.retrofit.create(NYTService.class);
        return nYTService.getTopic(desk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    //SEARCH ARTICLE

    public static Observable<NYTimesAPI> streamFetchSearchArticles(String query, String desk){
        NYTService nYTService = NYTService.retrofit.create(NYTService.class);
        return nYTService.getSearchArticles(query, desk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<NYTimesAPI> streamFetchSearchArticles(Search search){
        NYTService nYTService = NYTService.retrofit.create(NYTService.class);
        return nYTService.getSearchArticles(search.getQuery(), search.getNewsDesk(), search.getBeginDate(), search.getEndDate())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
