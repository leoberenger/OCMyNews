package com.openclassrooms.mynews.controllers.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.openclassrooms.mynews.controllers.activities.DetailActivity;
import com.openclassrooms.mynews.controllers.activities.SearchActivity;
import com.openclassrooms.mynews.models.API.NYTimesAPI;
import com.openclassrooms.mynews.models.API.Response;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.utils.DateMgr;
import com.openclassrooms.mynews.utils.ItemClickSupport;
import com.openclassrooms.mynews.api.NYTStreams;
import com.openclassrooms.mynews.views.DisplaySearchAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class DisplaySearchFragment extends DisplayFragment {

    private List<Response.Doc> searchArticles;
    private DisplaySearchAdapter searchAdapter;
    private final String SEARCH_TYPE_KEY = "searchType";
    private final String SEARCH_TYPE_TOPIC = "topic";
    private DateMgr dateMgr = DateMgr.getInstance();

    public DisplaySearchFragment() { }

    @Override
    protected Observable<NYTimesAPI> getStream() {

        Observable<NYTimesAPI> stream = null;

        Bundle args = getArguments();
        String searchType = args.getString(SEARCH_TYPE_KEY);

        switch (searchType) {

            //Retrieve stream from calling Activity

            case "topic":
                String topic = args.getString(SEARCH_TYPE_TOPIC);
                stream = NYTStreams.streamFetchTopic("news_desk:(%22" + topic + "%22)");
                break;

            case "query":
                mSearch = searchMgr.getSearchFromBundle(args);

                //If stream has no defined begin/end dates
                stream = (mSearch.getBeginDate() == 0 || mSearch.getEndDate() == 0) ?
                        NYTStreams.streamFetchSearchArticles(mSearch.getQuery(), mSearch.getNewsDesk()):
                        NYTStreams.streamFetchSearchArticles(mSearch);
                break;
        }
        return stream;
    }

    @Override
    protected void configureRecyclerView(){
        this.searchArticles = new ArrayList<>();
        this.searchAdapter = new DisplaySearchAdapter(this.searchArticles, Glide.with(this));
        this.mRecyclerView.setAdapter(this.searchAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        String articleUrl = searchAdapter.getResponse(position).getWebUrl();
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(EXTRA_ARTICLE_URL, articleUrl);
                        startActivity(intent);
                    }
                });
    }

    @Override
    protected void updateUI(NYTimesAPI responses){
        mSwipeRefreshLayout.setRefreshing(false);
        searchArticles.clear();
        List<Response.Doc> articles = responses.getResponse().getDocs();

        int lastReadPubDate = getArguments().getInt("lastReadPubDate",0);
        Log.e("DisplaySearchFragment", "lastReadPubDate = " + lastReadPubDate);

        if(lastReadPubDate!=0){
            for(int i = 0; i<articles.size(); i++){
                if(dateMgr.transformPublishedDate(articles.get(i).getPubDate()) < lastReadPubDate){
                    searchArticles.add(articles.get(i));
                }
            }
        }else {
            searchArticles.addAll(responses.getResponse().getDocs());
        }

        searchAdapter.notifyDataSetChanged();

        //no article to show -> alertDialog
        if(responses.getResponse().getDocs().isEmpty()) {
            showNoArticlesAlertDialog();
        }
    }

    private void showNoArticlesAlertDialog(){
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
