package com.openclassrooms.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.mynews.Models.Response;
import com.openclassrooms.mynews.Models.Result;
import com.openclassrooms.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by berenger on 06/03/2018.
 */

public class ArticleViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.fragment_main_item_title)
    TextView mTitle;
    @BindView(R.id.fragment_main_item_section)
    TextView mSection;
    @BindView(R.id.fragment_main_item_published_date)
    TextView mDate;
    @BindView(R.id.fragment_main_item_img)
    ImageView mImage;

    String imgUrl;

    public ArticleViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithTopStoriesArticle(Result result, RequestManager glide){
        this.mTitle.setText(result.getTitle());
        this.mSection.setText(result.getSection());
        //SUBSECTION
        this.mDate.setText(transformPublishedDate(result.getPublishedDate()));

        if(!result.getMultimedia().isEmpty())
            glide.load(result.getMultimedia().get(0).getUrl()).into(mImage);
        else
            glide.load("http://www.nytimes.com/services/mobile/img/ios-newsreader-icon.png").into(mImage);
    }

    public void updateWithMostPopularArticle(Result result, RequestManager glide){
        this.mTitle.setText(result.getTitle());
        this.mSection.setText(result.getSection());
        this.mDate.setText(transformPublishedDate(result.getPublishedDate()));

        if((!result.getMedia().isEmpty()) && (!result.getMedia().get(0).getMediaMetadata().isEmpty()))
            glide.load(result.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(mImage);
        else
            glide.load("http://www.nytimes.com/services/mobile/img/ios-newsreader-icon.png").into(mImage);
    }

    public void updateWithSearchArticle(Response.Doc response, RequestManager glide){
        this.mTitle.setText(response.getHeadline().getMain());
        this.mDate.setText(transformPublishedDate(response.getPubDate()));

        if(!response.getMultimedia().isEmpty())
            glide.load("https://static01.nyt.com/" + response.getMultimedia().get(0).getUrl()).into(mImage);
        else
            glide.load("http://www.nytimes.com/services/mobile/img/ios-newsreader-icon.png").into(mImage);
    }

    private String transformPublishedDate(String pubDate){
        //(Ex: 2018-03-08T05:44:00-05:00)
        return  pubDate.substring(8,10)         //Day   (ex:03)
                + "/" + pubDate.substring(5,7)  //Month (ex:08)
                + "/" + pubDate.substring(2,4); //Year  (ex:18)
    }
}
