package com.openclassrooms.mynews.Models;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.evernote.android.job.util.support.PersistableBundleCompat;

public class Search {

    private String query;
    private String newsDesk;
    private int beginDate;
    private int endDate;

    private String QUERY_KEY = "query";
    private String NEWS_DESK_KEY = "newsDesk";
    private String BEGIN_DATE_KEY = "beginDate";
    private String END_DATE_KEY = "endDate";

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
        return prefs.getString(QUERY_KEY, "");
    }

    public String getQuery(Bundle bundle){
        return bundle.getString(QUERY_KEY, "");
    }

    public String getQuery(PersistableBundleCompat bundle){
        return bundle.getString(QUERY_KEY, "");
    }


    public void setQuery(String query) {
        this.query = query;
    }

    public void setQuery(Bundle bundle, String query){
        bundle.putString(QUERY_KEY, query);
    }

    public void setQuery(PersistableBundleCompat bundle, String query){
        bundle.putString(QUERY_KEY, query);
    }

    public void setQuery(SharedPreferences prefs, String query){
        prefs.edit().putString(QUERY_KEY, query).apply();
    }

    //-------------------------------------
    // NEWS DESK
    //-------------------------------------

    public String getNewsDesk() {
        return newsDesk;
    }

    public String getNewsDesk(Bundle bundle){
        return bundle.getString(NEWS_DESK_KEY, "");
    }

    public String getNewsDesk(PersistableBundleCompat bundle){
        return bundle.getString(NEWS_DESK_KEY, "");
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
        bundle.putString(NEWS_DESK_KEY, newsDesk);
    }

    public void setNewsDesk(PersistableBundleCompat bundle, String newsDesk){
        bundle.putString(NEWS_DESK_KEY, newsDesk);
    }

    public void setNewsDesk(SharedPreferences prefs, String newsDesk){
        prefs.edit().putString(NEWS_DESK_KEY, newsDesk).apply();
    }

    //-------------------------------------
    // BEGIN DATE
    //-------------------------------------

    public int getBeginDate() {
        return beginDate;
    }

    public int getBeginDate(Bundle bundle) {
        return bundle.getInt(BEGIN_DATE_KEY, 0);
    }


    public void setBeginDate(int beginDate) {
        this.beginDate = beginDate;
    }

    public void setBeginDate(Bundle bundle, int beginDate){
        bundle.putInt(BEGIN_DATE_KEY, beginDate);
    }

    //-------------------------------------
    // END DATE
    //-------------------------------------

    public int getEndDate() {
        return endDate;
    }

    public int getEndDate(Bundle bundle) {
        return bundle.getInt(END_DATE_KEY, 0);
    }


    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(Bundle bundle, int endDate){
        bundle.putInt(END_DATE_KEY, endDate);
    }

    //-------------------------------------
    // OTHER
    //-------------------------------------

    public void setSearch(Intent i, String query, String desk, int begDate, int endDate){
        i.putExtra(QUERY_KEY, query);
        i.putExtra(NEWS_DESK_KEY, desk);
        i.putExtra(BEGIN_DATE_KEY, begDate);
        i.putExtra(END_DATE_KEY, endDate);
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
