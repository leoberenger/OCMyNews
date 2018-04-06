package com.openclassrooms.mynews.Models;

public class Query {

    private String query;
    private String newsDesk;
    private int beginDate;
    private int endDate;

    public Query(String newsDesk) {
        this.newsDesk = newsDesk;
    }


    public Query(String query, String newsDesk) {
        this.query = query;
        this.newsDesk = newsDesk;
    }

    public Query(String query, String newsDesk, int beginDate, int endDate) {
        this.query = query;
        this.newsDesk = newsDesk;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public int getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(int beginDate) {
        this.beginDate = beginDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }
}
