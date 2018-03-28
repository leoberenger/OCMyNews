package com.openclassrooms.mynews.Controllers.Fragments.ArticleViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.openclassrooms.mynews.Controllers.Activities.DetailActivity;
import com.openclassrooms.mynews.Models.NYTimesAPI;
import com.openclassrooms.mynews.Models.Result;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.ItemClickSupport;
import com.openclassrooms.mynews.Utils.NYTStreams;
import com.openclassrooms.mynews.Views.ArticleViewHolder;
import com.openclassrooms.mynews.Views.MostPopularAdapter;
import com.openclassrooms.mynews.Views.SearchArticleAdapter;
import com.openclassrooms.mynews.Views.TopStoriesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public abstract class BaseFragment extends Fragment {

    //Force developer to implement those methods
    protected abstract BaseFragment newInstance();
    //protected abstract io.reactivex.Observable<com.openclassrooms.mynews.Models.NYTimesAPI> getStream();
    //protected abstract RecyclerView.Adapter<ArticleViewHolder> getAdapter();

    private io.reactivex.Observable<com.openclassrooms.mynews.Models.NYTimesAPI> getStream(){
        return NYTStreams.streamFetchTopStories();
        // stream = NYTStreams.streamFetchMostPopular();
        // stream = NYTStreams.streamFetchSearchArticles();
    }

    private RecyclerView.Adapter<ArticleViewHolder> getAdapter(){
        return new TopStoriesAdapter(this.articles, Glide.with(this));
        //this.adapter = new TopStoriesAdapter(this.articles, Glide.with(this));
        //this.mAdapter = new MostPopularAdapter(this.articles, Glide.with(this));
        //this.mAdapter = new SearchArticleAdapter(this.articles, Glide.with(this));
    }


    // FOR DESIGN
    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_main_swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    //FOR DATA
    private Disposable mDisposable;
    private List<Result> articles;
    //private List<Response.Doc> articles;
    protected RecyclerView.Adapter<ArticleViewHolder> adapter;
    //private MostPopularAdapter mAdapter;
    //private SearchArticleAdapter mAdapter;
    protected io.reactivex.Observable<com.openclassrooms.mynews.Models.NYTimesAPI> stream;

    String EXTRA_ARTICLE_URL = "EXTRA_ARTICLE_URL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        stream = getStream();
        adapter = getAdapter();

        this.executeHttpRequestWithRetrofit();
        this.configureSwipeRefreshLayout();
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
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

    private void configureSwipeRefreshLayout(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    private void configureRecyclerView(){
        this.articles = new ArrayList<>();
        this.mRecyclerView.setAdapter(this.adapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

//PROBLEME ICI
                        String articleUrl = "url temporaire";
                        //String articleUrl = adapter.getResult(position).getUrl();
                        //String articleUrl = mAdapter.getResult(position).getUrl();
                        //String articleUrl = mAdapter.getResponse(position).getWebUrl();

                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(EXTRA_ARTICLE_URL, articleUrl);
                        startActivity(intent);
                    }
                });
    }

    // -----------------
    // HTTP REQUEST (RxJava)
    // -----------------

    private void executeHttpRequestWithRetrofit(){
        this.mDisposable = stream
                .subscribeWith(new DisposableObserver<NYTimesAPI>(){
                    @Override
                    public void onNext(NYTimesAPI articles) {
                        Log.e("TAG", "On Next");
                        updateUI(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "On Error"+Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG", "On Complete");
                    }
                });
    }

    private void disposeWhenDestroy(){
        if(this.mDisposable != null && !this.mDisposable.isDisposed())
            this.mDisposable.dispose();
    }

    // -----------------
    // UPDATE UI
    // -----------------

    private void updateUI(NYTimesAPI results){
        mSwipeRefreshLayout.setRefreshing(false);
        articles.clear();
        articles.addAll(results.getResults());
        //articles.addAll(results.getResponse().getDocs());
        adapter.notifyDataSetChanged();
    }
}