package com.openclassrooms.mynews.Controllers.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.openclassrooms.mynews.Controllers.Activities.DetailActivity;
import com.openclassrooms.mynews.Controllers.Activities.SearchActivity;
import com.openclassrooms.mynews.Models.NYTimesAPI;
import com.openclassrooms.mynews.Models.Response;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.ItemClickSupport;
import com.openclassrooms.mynews.Utils.NYTStreams;
import com.openclassrooms.mynews.Views.DisplayArticlesSearchAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplaySearchFragment extends BaseDisplayFragment {

    public DisplaySearchFragment() { }

    //FOR TOPIC SEARCH
    public static DisplaySearchFragment newInstance(String searchType, String newsDesk){
        DisplaySearchFragment displaySearchFragment = new DisplaySearchFragment();

        Bundle args = new Bundle();
        args.putString("searchType", searchType);
        args.putString("newsDesk", newsDesk);
        displaySearchFragment.setArguments(args);

        return displaySearchFragment;
    }

    //FOR ARTICLES SEARCH
    public static DisplaySearchFragment newInstance(String searchType, String query, String newsDesk, int beginDate, int endDate){
        DisplaySearchFragment displaySearchFragment = new DisplaySearchFragment();

        Bundle args = new Bundle();
        args.putString("searchType", searchType);
        args.putString("query", query);
        args.putString("newsDesk", newsDesk);
        args.putInt("beginDate", beginDate);
        args.putInt("endDate", endDate);
        displaySearchFragment.setArguments(args);

        return displaySearchFragment;
    }

    @Override
    protected Observable<NYTimesAPI> getStream() {

        Observable<NYTimesAPI> stream = null;

        Bundle args = getArguments();
        String searchType = args.getString("searchType");

        switch (searchType) {

            case "topic":
                String mNewsDesk = "news_desk:(%22" + args.getString("newsDesk", "") + "%22)";
                stream = NYTStreams.streamFetchTopic(mNewsDesk);
                break;

            case "query":
                String query = args.getString("query", "");
                String newsDesk = args.getString("newsDesk", "");
                int beginDate = args.getInt("beginDate", 0);
                int endDate = args.getInt("endDate", 0);

                if((beginDate!=0) && (endDate !=0))
                    stream = NYTStreams.streamFetchSearchArticles(query, newsDesk, beginDate, endDate);
                else
                    stream = NYTStreams.streamFetchSearchArticles(query, newsDesk);
                break;
        }
        return stream;
    }


    //-----------------------------------------
    // OVERRIDING BASE FRAGMENT METHODS TO FIT SEARCH ARTICLES API List
    //-----------------------------------------

    //FOR DATA
    private List<Response.Doc> articles;
    private DisplayArticlesSearchAdapter mAdapter;

    protected void configureRecyclerView(){
        this.articles = new ArrayList<>();
        this.mAdapter = new DisplayArticlesSearchAdapter(this.articles, Glide.with(this));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    protected void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        String articleUrl = mAdapter.getResponse(position).getWebUrl();
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(EXTRA_ARTICLE_URL, articleUrl);
                        startActivity(intent);
                    }
                });
    }

    protected void updateUI(NYTimesAPI responses){
        mSwipeRefreshLayout.setRefreshing(false);
        articles.clear();
        articles.addAll(responses.getResponse().getDocs());
        mAdapter.notifyDataSetChanged();

        //no article to show -> alertDialog
        if(responses.getResponse().getDocs().isEmpty()) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getContext());
            }
            builder.setMessage("No Articles for this search")
                    .setPositiveButton("New Search", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getActivity(), SearchActivity.class);
                            startActivity(intent);
                        }})
                    .show();
        }
    }
}

