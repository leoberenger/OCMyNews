package com.openclassrooms.mynews.Models;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class Search {

    private String query;
    private String newsDesk;
    private int beginDate;
    private int endDate;

    private String BUNDLE_QUERY = "query";
    private String BUNDLE_NEWS_DESK = "newsDesk";
    private String BUNDLE_BEGIN_DATE = "beginDate";
    private String BUNDLE_END_DATE = "endDate";

    //-------------------------------------
    // CONSTRUCTORS
    //-------------------------------------

    public Search(){}

    public Search(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public Search(String query, String newsDesk) {
        this.query = query;
        this.newsDesk = newsDesk;
    }

    public Search(String query, String newsDesk, int beginDate, int endDate) {
        this.query = query;
        this.newsDesk = newsDesk;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    //-------------------------------------
    // QUERY
    //-------------------------------------

    public String getQuery() {
        return query;
    }

    public String getQuery(EditText editText){
        return editText.getText().toString();
    }

    public String getQuery(SharedPreferences prefs){
        return prefs.getString(BUNDLE_QUERY, "");
    }

    public String getQuery(Bundle bundle){
        return bundle.getString(BUNDLE_QUERY, "");
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setQuery(Bundle bundle, String query){
        bundle.putString(BUNDLE_QUERY, query);
    }

    //-------------------------------------
    // NEWS DESK
    //-------------------------------------

    public String getNewsDesk() {
        return newsDesk;
    }

    public String getNewsDesk(Bundle bundle){
        return bundle.getString(BUNDLE_NEWS_DESK, "");
    }

    public String getNewsDesk(boolean [] desksArray, String [] desksName){

        StringBuilder str = new StringBuilder("news_desk:(");

        for (int i = 0; i < desksArray.length; i++) {
            if (desksArray[i])
                str.append(desksName[i]);
        }

        str.append(")");

        return str.toString();
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public void setNewsDesk(Bundle bundle, String newsDesk){
        bundle.putString(BUNDLE_NEWS_DESK, newsDesk);
    }

    //-------------------------------------
    // BEGIN DATE
    //-------------------------------------

    public int getBeginDate() {
        return beginDate;
    }

    public int getBeginDate(Bundle bundle) {
        return bundle.getInt(BUNDLE_BEGIN_DATE, 0);
    }

    public void setBeginDate(int beginDate) {
        this.beginDate = beginDate;
    }

    public void setBeginDate(Bundle bundle, int beginDate){
        bundle.putInt(BUNDLE_BEGIN_DATE, beginDate);
    }

    //-------------------------------------
    // END DATE
    //-------------------------------------

    public int getEndDate() {
        return endDate;
    }

    public int getEndDate(Bundle bundle) {
        return bundle.getInt(BUNDLE_END_DATE, 0);
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(Bundle bundle, int endDate){
        bundle.putInt(BUNDLE_END_DATE, endDate);
    }

    //-------------------------------------
    // OTHER
    //-------------------------------------

    public void setSearch(Intent i, String query, String desk, int begDate, int endDate){
        String EXTRA_QUERY = "EXTRA_QUERY";
        String EXTRA_NEWS_DESKS = "EXTRA_NEWS_DESKS";
        String EXTRA_BEGIN_DATE = "EXTRA_BEGIN_DATE";
        String EXTRA_END_DATE = "EXTRA_END_DATE";

        i.putExtra(EXTRA_QUERY, query);
        i.putExtra(EXTRA_NEWS_DESKS, desk);
        i.putExtra(EXTRA_BEGIN_DATE, begDate);
        i.putExtra(EXTRA_END_DATE, endDate);
    }

    public void setSearch(Bundle b, String query, String desk, int begDate, int endDate){
        setQuery(b, query);
        setNewsDesk(b, desk);
        setBeginDate(b, begDate);
        setEndDate(b, endDate);
    }

    public boolean checkMin1DeskSelected(boolean [] desks){
        boolean min1DeskIsSelected = false;

        for(int i = 0; i<desks.length; i++){
            if(desks[i])
                min1DeskIsSelected = true;
        }

        return min1DeskIsSelected;
    }
}
