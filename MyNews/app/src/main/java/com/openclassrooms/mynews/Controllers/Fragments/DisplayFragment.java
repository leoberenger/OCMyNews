package com.openclassrooms.mynews.Controllers.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.openclassrooms.mynews.Controllers.Activities.DetailActivity;
import com.openclassrooms.mynews.Controllers.Activities.SearchActivity;
import com.openclassrooms.mynews.Models.NYTimesAPI;
import com.openclassrooms.mynews.Models.Response;
import com.openclassrooms.mynews.Models.Result;
import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.ItemClickSupport;
import com.openclassrooms.mynews.Utils.NYTStreams;
import com.openclassrooms.mynews.Views.DisplaySearchAdapter;
import com.openclassrooms.mynews.Views.DisplayStoriesAdapter;

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
public class DisplayFragment extends android.support.v4.app.Fragment {

    // FOR DESIGN
    @BindView(R.id.fragment_main_recycler_view)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.fragment_main_swipe_container)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    //FOR DATA
    protected Disposable mDisposable;

    private List<Result> articles;
    private DisplayStoriesAdapter adapter;

    protected String EXTRA_ARTICLE_URL = "EXTRA_ARTICLE_URL";
    static Search search = new Search();

    public DisplayFragment() { }

    protected Observable<NYTimesAPI> getStream() {

        Observable<NYTimesAPI> stream = null;

        Bundle args = getArguments();
        String searchType = args.getString((getResources().getString(R.string.bundle_search_type)));

        switch (searchType) {

            case "topStories":
                stream = NYTStreams.streamFetchTopStories();
                break;

            case "mostPopular":
                stream = NYTStreams.streamFetchMostPopular();
                break;
        }

        return stream;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        Observable<NYTimesAPI> mStream = getStream();

        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit(mStream);
        this.configureSwipeRefreshLayout(mStream);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    protected void configureSwipeRefreshLayout(final Observable<NYTimesAPI> stream){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit(stream);
            }
        });
    }

    protected void configureRecyclerView(){
        this.articles = new ArrayList<>();
        this.adapter = new DisplayStoriesAdapter(this.articles, Glide.with(this));
        this.mRecyclerView.setAdapter(this.adapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    protected void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        String articleUrl = adapter.getResult(position).getUrl();
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(EXTRA_ARTICLE_URL, articleUrl);
                        startActivity(intent);
                    }
                });
    }



    // -----------------
    // HTTP REQUEST (RxJava)
    // -----------------

    protected void executeHttpRequestWithRetrofit(Observable<NYTimesAPI> stream){
        this.mDisposable = stream
                .subscribeWith(new DisposableObserver<NYTimesAPI>(){
                    @Override
                    public void onNext(NYTimesAPI articles) {
                        Log.e("TopStoriesFragment", "On Next");
                        updateUI(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TopStoriesFragment", "On Error"+Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TopStoriesFragment", "On Complete");
                    }
                });
    }

    protected void disposeWhenDestroy(){
        if(this.mDisposable != null && !this.mDisposable.isDisposed())
            this.mDisposable.dispose();
    }

    // -----------------
    // UPDATE UI
    // -----------------

    protected void updateUI(NYTimesAPI results){
        mSwipeRefreshLayout.setRefreshing(false);
        articles.clear();
        articles.addAll(results.getResults());
        adapter.notifyDataSetChanged();
    }
}
