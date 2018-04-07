package com.openclassrooms.mynews.Controllers.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.openclassrooms.mynews.Views.DisplaySearchAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class DisplaySearchFragment extends DisplayFragment {

    private List<Response.Doc> searchArticles;
    private DisplaySearchAdapter searchAdapter;

    public DisplaySearchFragment() { }

    protected Observable<NYTimesAPI> getStream() {

        Observable<NYTimesAPI> stream = null;

        Bundle args = getArguments();
        String searchType = args.getString((getResources().getString(R.string.bundle_search_type)));

        switch (searchType) {

            case "topic":
                String topic = args.getString(getResources().getString(R.string.bundle_search_type_topic));
                String mNewsDesk = "news_desk:(%22" + topic + "%22)";
                stream = NYTStreams.streamFetchTopic(mNewsDesk);
                break;

            case "query":
                String query = search.getQuery(args);
                String newsDesk = search.getNewsDesk(args);
                int beginDate = search.getBeginDate(args);
                int endDate = search.getEndDate(args);

                if((beginDate!=0) && (endDate !=0))
                    stream = NYTStreams.streamFetchSearchArticles(query, newsDesk, beginDate, endDate);
                else
                    stream = NYTStreams.streamFetchSearchArticles(query, newsDesk);
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
        searchArticles.addAll(responses.getResponse().getDocs());
        searchAdapter.notifyDataSetChanged();

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
