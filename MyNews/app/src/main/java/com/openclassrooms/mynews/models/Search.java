package com.openclassrooms.mynews.models;

public class Search {

    private final String searchType;
    private String query;
    private String newsDesk;
    private int beginDate;
    private int endDate;

    //--------------------------
    // CONSTRUCTORS
    //--------------------------

    public Search(String searchType, String query, String newsDesk, int beginDate, int endDate) {
        this.searchType = searchType;
        this.query = query;
        this.newsDesk = newsDesk;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }


    //--------------------------
    // GETTERS
    //--------------------------

    public String getQuery() {
        return query;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public int getBeginDate() {
        return beginDate;
    }

    public int getEndDate() {
        return endDate;
    }

    //--------------------------
    // SETTERS
    //--------------------------

    public void setQuery(String query) {
        this.query = query;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public void setBeginDate(int beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }
}

