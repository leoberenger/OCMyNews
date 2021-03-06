package com.openclassrooms.mynews.utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.openclassrooms.mynews.models.Search;

public class SearchMgr {

    private static final SearchMgr ourInstance = new SearchMgr();

    private Search search;

    private final String SEARCH_TYPE_KEY = "searchType";
    private final String QUERY_KEY = "query";

    private final String NEWS_DESK_KEY = "newsDesk";
    private final String BEGIN_DATE_KEY = "beginDate";
    private final String END_DATE_KEY = "endDate";


    public static SearchMgr getInstance() {
        return ourInstance;
    }

    private SearchMgr() {}

    //-------------------------------------
    // GET METHODS
    //-------------------------------------

    public Search getSearchFromIntent(Intent i) {

        String query = i.getStringExtra(QUERY_KEY);
        String desk = i.getStringExtra(NEWS_DESK_KEY);
        int begD = i.getIntExtra(BEGIN_DATE_KEY, 0);
        int endD = i.getIntExtra(END_DATE_KEY, 0);

        return search = new Search(QUERY_KEY, query, desk, begD, endD);
    }

    public Search getSearchFromBundle(Bundle bundle){

        String query = bundle.getString(QUERY_KEY, "");
        String desk = bundle.getString(NEWS_DESK_KEY, "");
        int begD = bundle.getInt(BEGIN_DATE_KEY, 0);
        int endD = bundle.getInt(END_DATE_KEY, 0);

        return search = new Search(QUERY_KEY, query, desk, begD, endD);
    }

    public Search getSearchFromPersistBundle(PersistableBundleCompat bundle){

        String query = bundle.getString(QUERY_KEY, "");
        String desk = bundle.getString(NEWS_DESK_KEY, "");
        int begD = bundle.getInt(BEGIN_DATE_KEY, 0);
        int endD = bundle.getInt(END_DATE_KEY, 0);

        return search = new Search(QUERY_KEY, query, desk, begD, endD);
    }


    //-------------------------------------
    // SET METHODS
    //-------------------------------------

    public void setSearchToPrefs(SharedPreferences prefs, String query, boolean [] newsDesksSelected, boolean switchEnabled){

        //Save query
        prefs.edit().putString(QUERY_KEY, query).apply();

        //Save NewsDesksArray
        for(int i = 0; i<newsDesksSelected.length; i++) {
            prefs.edit().putBoolean("desk" + i, newsDesksSelected[i]).apply();
        }
        //Save switch
        prefs.edit().putBoolean("switchEnabled", switchEnabled).apply();
    }

    public void setSearchToIntent(Intent i, Search search){

        String query = search.getQuery();
        String desk = search.getNewsDesk();
        int begD = search.getBeginDate();
        int endD = search.getEndDate();

        i.putExtra(SEARCH_TYPE_KEY, QUERY_KEY);
        i.putExtra(QUERY_KEY, query);
        i.putExtra(NEWS_DESK_KEY, desk);
        i.putExtra(BEGIN_DATE_KEY, begD);
        i.putExtra(END_DATE_KEY, endD);
    }

    public void setSearchToBundle(Bundle bundle, Search search){

        String query = search.getQuery();
        String desk = search.getNewsDesk();
        int begD = search.getBeginDate();
        int endD = search.getEndDate();

        bundle.putString(SEARCH_TYPE_KEY, QUERY_KEY);
        bundle.putString(QUERY_KEY, query);
        bundle.putString(NEWS_DESK_KEY, desk);
        bundle.putInt(BEGIN_DATE_KEY, begD);
        bundle.putInt(END_DATE_KEY, endD);
    }

    public void setSearchToPersistBundle(PersistableBundleCompat bundle, Search search){

        String query = search.getQuery();
        String desk = search.getNewsDesk();
        int begD = search.getBeginDate();
        int endD = search.getEndDate();

        bundle.putString(SEARCH_TYPE_KEY, QUERY_KEY);
        bundle.putString(QUERY_KEY, query);
        bundle.putString(NEWS_DESK_KEY, desk);
        bundle.putInt(BEGIN_DATE_KEY, begD);
        bundle.putInt(END_DATE_KEY, endD);
    }


    //-------------------------------------
    // OTHER METHODS
    //-------------------------------------

    //Retrieve desks selected from boolean array
    public String newsDesks(boolean [] desksArray){

        String [] newsDesksName = {"Arts", "Business", "Entrepreneur", "Politics", "Sports", "Travel"};

        StringBuilder str = new StringBuilder("news_desk:(");
        for(int i = 0; i < desksArray.length; i++) {
            if (desksArray[i]) {
                String topic = " %22" + newsDesksName[i] + "%22";
                str.append(topic);
            }
        }
        str.append(")");

        return str.toString();
    }

    //Check if all desks are set to false
    public boolean noDeskSelected(boolean [] desks){
        boolean noDeskSelected = true;

        for(boolean desk:desks) {
            if(desk) noDeskSelected = false;
        }

        return noDeskSelected;
    }
}
