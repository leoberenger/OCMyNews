package com.openclassrooms.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.mynews.Models.Response;
import com.openclassrooms.mynews.Models.Result;
import com.openclassrooms.mynews.R;

import java.util.List;

public abstract class BaseAdapter extends RecyclerView.Adapter<ArticleViewHolder>{

        //FOR DATA
        List<Result> mArticles; // List<Response> mArticles;
        RequestManager glide;

        //CONSTRUCTOR
        public BaseAdapter(List<Result> articles, RequestManager glide){
            //public BaseAdapter(List<Response> articles, RequestManager glide){
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
            viewHolder.updateWithTopStoriesArticle(this.mArticles.get(position), this.glide);
            //viewHolder.updateWithMostPopularArticle(this.mArticles.get(position), this.glide);
            //viewHolder.updateWithSearchArticle(this.mArticles.get(position), this.glide);
        }

        @Override
        public int getItemCount() {
            return this.mArticles.size();
        }
}
