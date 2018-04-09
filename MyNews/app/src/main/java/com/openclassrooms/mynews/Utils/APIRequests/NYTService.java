package com.openclassrooms.mynews.Utils.APIRequests;


import com.openclassrooms.mynews.Models.API.NYTimesAPI;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by berenger on 01/03/2018.
 */

public interface NYTService {

    String apiKeyQuery = "api-key=186147632b12462f92f003d7b33d2786";

    //TOP STORIES
    @GET("topstories/v2/home.json?" + apiKeyQuery)
    Observable<NYTimesAPI> getTopStories();

    //MOST POPULAR
    @GET("mostpopular/v2/mostviewed/all-sections/1.json?" + apiKeyQuery)
    Observable<NYTimesAPI> getMostPopular();

    //TOPIC
    @GET("search/v2/articlesearch.json?" + apiKeyQuery)
    Observable<NYTimesAPI> getTopic(
            @Query("fq") String desk                //&fq=news_desk:(%22Travel%22)"
    );

    //SEARCH ARTICLE
    @GET("search/v2/articlesearch.json?" + apiKeyQuery)
    Observable<NYTimesAPI> getSearchArticles(
            @Query("query") String query,
            @Query("fq") String desk               //&fq=news_desk:(%22Travel%22)" +
    );

    @GET("search/v2/articlesearch.json?" + apiKeyQuery)
    Observable<NYTimesAPI> getSearchArticles(
            @Query("query") String query,
            @Query("fq") String desk,               //&fq=news_desk:(%22Travel%22)" +
            @Query("begin_date") int beginDate,     //"&begin_date=20170910" +
            @Query("end_date") int endDate          //"&end_date=20171001"
    );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
