package com.openclassrooms.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.mynews.Models.Result;
import com.openclassrooms.mynews.R;

import java.util.List;

/**
 * Created by berenger on 06/03/2018.
 */

public class DisplayStoriesAdapter extends RecyclerView.Adapter<ArticleViewHolder>{

    //FOR DATA
    private List<Result> mArticles;
    public RequestManager glide;

    //CONSTRUCTOR
    public DisplayStoriesAdapter(List<Result> articles, RequestManager glide){
        this.mArticles = articles;
        this.glide = glide;
    }

    public Result getResult(int position){
        return this.mArticles.get(position);
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent, false);

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder viewHolder, int position){
        viewHolder.updateWithArticle(this.mArticles.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return this.mArticles.size();
    }
}
